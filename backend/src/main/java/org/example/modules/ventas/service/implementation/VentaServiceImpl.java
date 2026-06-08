package org.example.modules.ventas.service.implementation;

import org.example.modules.ventas.repository.VentaRepository;

import org.example.modules.ventas.entity.Venta;

import org.example.modules.productos.entity.Producto;

import org.example.modules.clientes.entity.Cliente;

import org.example.modules.auth.entity.Usuario;

import org.example.modules.ventas.dto.DetalleVentaDTO;
import org.example.modules.ventas.entity.Venta;
import org.example.modules.ventas.entity.DetalleVenta;
import org.example.modules.inventario.entity.TipoMovimiento;
import org.example.modules.ventas.dto.VentaDTO;

import org.example.exception.RecursoNoEncontradoException;
import org.example.exception.ReglaNegocioException;
import org.example.modules.ventas.mapper.VentaMapper;

import org.example.modules.ventas.service.interfaces.VentaService;
import org.example.modules.productos.service.interfaces.ProductoService;
import org.example.modules.clientes.service.interfaces.ClienteService;
import org.example.modules.auth.service.interfaces.AuthService;
import org.example.modules.inventario.service.interfaces.MovimientoInventarioService;
import org.example.modules.auditoria.service.interfaces.AuditoriaService;
import org.example.common.service.BaseServiceImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
/**
 * Implementación del servicio de Venta.
 */
public class VentaServiceImpl extends BaseServiceImpl<Venta, VentaDTO, Long> implements VentaService {

    private final VentaRepository ventaRepository;
    private final ClienteService clienteService;
    private final ProductoService productoService;
    private final AuthService authService;
    private final MovimientoInventarioService movimientoInventarioService;
    private final AuditoriaService auditoriaService;

    public VentaServiceImpl(VentaRepository ventaRepository, ClienteService clienteService,
                            ProductoService productoService, AuthService authService,
                            MovimientoInventarioService movimientoInventarioService,
                            AuditoriaService auditoriaService) {
        this.ventaRepository = ventaRepository;
        this.clienteService = clienteService;
        this.productoService = productoService;
        this.authService = authService;
        this.movimientoInventarioService = movimientoInventarioService;
        this.auditoriaService = auditoriaService;
    }

    @Override
    protected JpaRepository<Venta, Long> getRepository() {
        return ventaRepository;
    }

    @Override
    protected VentaDTO toDTO(Venta entity) {
        return VentaMapper.toDTO(entity);
    }

    @Override
    protected Venta toEntity(VentaDTO dto) {
        return VentaMapper.toEntity(dto);
    }

    @Override
    @Transactional
    /**
     * Guarda o crea un registro.
     * @param dto el objeto a guardar
     * @return el objeto guardado
     */
    public VentaDTO save(VentaDTO dto) {
        if (dto.getDetalles() == null || dto.getDetalles().isEmpty()) {
            throw new ReglaNegocioException("La venta debe tener al menos un detalle.");
        }

        Venta venta = toEntity(dto);

        if (dto.getClienteId() != null) {
            Cliente cliente = clienteService.getEntityById(dto.getClienteId());
            venta.setCliente(cliente);
        }

        // Asignar el vendedor logueado
        Usuario vendedor = authService.getUsuarioAutenticado();
        venta.setVendedor(vendedor);

        BigDecimal total = BigDecimal.ZERO;
        venta.setDetalles(new ArrayList<>());

        for (DetalleVentaDTO detalleDTO : dto.getDetalles()) {
            Producto producto = productoService.getEntityById(detalleDTO.getProductoId());

            if (producto.getStock() < detalleDTO.getCantidad()) {
                throw new ReglaNegocioException("Stock insuficiente para el producto: " + producto.getNombre());
            }

            Integer stockAnterior = producto.getStock();
            Integer stockNuevo = stockAnterior - detalleDTO.getCantidad();
            productoService.actualizarStock(producto.getId(), -detalleDTO.getCantidad());

            // Crear Movimiento de Inventario Automático usando el servicio
            movimientoInventarioService.registrarMovimiento(producto, TipoMovimiento.SALIDA, detalleDTO.getCantidad(), stockAnterior, stockNuevo, vendedor, "Venta realizada");

            DetalleVenta detalle = VentaMapper.detalleToEntity(detalleDTO);
            detalle.setVenta(venta);
            detalle.setProducto(producto);
            detalle.setPrecioUnitario(producto.getPrecio());
            
            BigDecimal subtotal = producto.getPrecio().multiply(BigDecimal.valueOf(detalleDTO.getCantidad()));
            detalle.setSubtotal(subtotal);
            
            total = total.add(subtotal);
            venta.getDetalles().add(detalle);
        }

        venta.setTotal(total);
        VentaDTO resultado = toDTO(ventaRepository.save(venta));
        auditoriaService.registrar("CREATE", "Venta", resultado.getId(), "Venta registrada por $" + total);
        return resultado;
    }

    @Override
    public VentaDTO update(Long id, VentaDTO dto) {
        throw new ReglaNegocioException("Las ventas no pueden ser modificadas una vez registradas.");
    }

    @Override
    public void delete(Long id) {
        throw new ReglaNegocioException("Las ventas no pueden ser eliminadas. Considere implementar un sistema de devoluciones/anulaciones.");
    }
}
