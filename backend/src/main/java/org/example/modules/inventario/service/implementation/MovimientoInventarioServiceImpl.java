package org.example.modules.inventario.service.implementation;

import org.example.modules.inventario.dto.MovimientoInventarioDTO;
import org.example.modules.inventario.entity.MovimientoInventario;
import org.example.modules.productos.entity.Producto;
import org.example.modules.inventario.entity.TipoMovimiento;
import org.example.modules.auth.entity.Usuario;
import org.example.exception.RecursoNoEncontradoException;
import org.example.exception.ReglaNegocioException;
import org.example.modules.inventario.mapper.MovimientoInventarioMapper;
import org.example.modules.inventario.repository.MovimientoInventarioRepository;
import org.example.modules.productos.repository.ProductoRepository;
import org.example.modules.auth.repository.UsuarioRepository;
import org.example.modules.inventario.service.interfaces.MovimientoInventarioService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import org.example.common.service.BaseServiceImpl;

@Service
/**
 * Implementación del servicio de MovimientoInventario.
 */
public class MovimientoInventarioServiceImpl extends BaseServiceImpl<MovimientoInventario, MovimientoInventarioDTO, Long> implements MovimientoInventarioService {

    private final MovimientoInventarioRepository repository;
    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;

    public MovimientoInventarioServiceImpl(MovimientoInventarioRepository repository, ProductoRepository productoRepository, UsuarioRepository usuarioRepository) {
        this.repository = repository;
        this.productoRepository = productoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    protected JpaRepository<MovimientoInventario, Long> getRepository() {
        return repository;
    }

    @Override
    protected MovimientoInventarioDTO toDTO(MovimientoInventario entity) {
        return MovimientoInventarioMapper.toDTO(entity);
    }

    @Override
    protected MovimientoInventario toEntity(MovimientoInventarioDTO dto) {
        return MovimientoInventarioMapper.toEntity(dto);
    }

    @Override
    @Transactional
    /**
     * Guarda o crea un registro.
     * @param dto el objeto a guardar
     * @return el objeto guardado
     */
    public MovimientoInventarioDTO save(MovimientoInventarioDTO dto) {
        if (dto.getCantidad() == null || dto.getCantidad() <= 0) {
            throw new ReglaNegocioException("La cantidad debe ser mayor a 0.");
        }

        Producto productoBD = productoRepository.findById(dto.getProductoId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto con ID " + dto.getProductoId() + " no encontrado."));

        Integer stockPrevio = productoBD.getStock();
        Integer stockNuevo = stockPrevio;

        if (dto.getTipoMovimiento() == TipoMovimiento.ENTRADA || dto.getTipoMovimiento() == TipoMovimiento.AJUSTE) {
            stockNuevo += dto.getCantidad();
        } else if (dto.getTipoMovimiento() == TipoMovimiento.SALIDA || dto.getTipoMovimiento() == TipoMovimiento.MERMA) {
            if (stockPrevio < dto.getCantidad()) {
                throw new ReglaNegocioException("Stock insuficiente para realizar la operación.");
            }
            stockNuevo -= dto.getCantidad();
        }

        productoBD.setStock(stockNuevo);
        productoRepository.save(productoBD);

        MovimientoInventario entity = toEntity(dto);
        entity.setProducto(productoBD);
        entity.setStockPrevio(stockPrevio);
        entity.setStockActual(stockNuevo);
        
        // Obtener usuario actual del SecurityContext
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String correo = ((UserDetails) principal).getUsername();
            Usuario usuario = usuarioRepository.findByCorreo(correo).orElse(null);
            entity.setResponsable(usuario);
        }

        return toDTO(repository.save(entity));
    }

    @Override
    public MovimientoInventarioDTO update(Long id, MovimientoInventarioDTO dto) {
        throw new ReglaNegocioException("Los movimientos de inventario no pueden ser editados para mantener la integridad del Kardex. Debe registrar un ajuste compensatorio.");
    }

    @Override
    public void delete(Long id) {
        throw new ReglaNegocioException("Los movimientos de inventario no pueden ser eliminados para mantener la integridad del Kardex. Debe registrar un ajuste compensatorio.");
    }

    @Override
    public List<MovimientoInventarioDTO> buscarPorProducto(Long productoId) {
        return repository.findByProductoIdOrderByFechaMovimientoDesc(productoId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void registrarMovimiento(Producto producto, TipoMovimiento tipo, Integer cantidad, Integer stockAnterior, Integer stockNuevo, Usuario responsable, String motivo) {
        MovimientoInventario movimiento = new MovimientoInventario();
        movimiento.setProducto(producto);
        movimiento.setTipoMovimiento(tipo);
        movimiento.setCantidad(cantidad);
        movimiento.setStockPrevio(stockAnterior);
        movimiento.setStockActual(stockNuevo);
        movimiento.setResponsable(responsable);
        movimiento.setMotivo(motivo);
        repository.save(movimiento);
    }
}
