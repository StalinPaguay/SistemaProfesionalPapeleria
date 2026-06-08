package org.example.modules.compras.service.implementation;

import org.example.modules.compras.dto.CompraDTO;
import org.example.modules.detallecompra.dto.DetalleCompraDTO;
import org.example.modules.compras.entity.Compra;
import org.example.modules.detallecompra.entity.DetalleCompra;
import org.example.modules.productos.entity.Producto;
import org.example.modules.proveedores.entity.Proveedor;
import org.example.exception.RecursoNoEncontradoException;
import org.example.exception.ReglaNegocioException;
import org.example.modules.compras.mapper.CompraMapper;
import org.example.modules.compras.repository.CompraRepository;
import org.example.modules.productos.service.interfaces.ProductoService;
import org.example.modules.proveedores.service.interfaces.ProveedorService;
import org.example.modules.compras.service.interfaces.CompraService;
import org.example.common.service.BaseServiceImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
/**
 * Implementación del servicio de Compra.
 */
public class CompraServiceImpl extends BaseServiceImpl<Compra, CompraDTO, Long> implements CompraService {

    private final CompraRepository repository;
    private final ProveedorService proveedorService;
    private final ProductoService productoService;

    public CompraServiceImpl(CompraRepository repository, ProveedorService proveedorService, ProductoService productoService) {
        this.repository = repository;
        this.proveedorService = proveedorService;
        this.productoService = productoService;
    }

    @Override
    protected JpaRepository<Compra, Long> getRepository() {
        return repository;
    }

    @Override
    protected CompraDTO toDTO(Compra entity) {
        return CompraMapper.toDTO(entity);
    }

    @Override
    protected Compra toEntity(CompraDTO dto) {
        return CompraMapper.toEntity(dto);
    }

    @Override
    @Transactional
    /**
     * Guarda o crea un registro.
     * @param dto el objeto a guardar
     * @return el objeto guardado
     */
    public CompraDTO save(CompraDTO dto) {
        if (dto.getProveedorId() == null) {
            throw new ReglaNegocioException("Debe asignar un proveedor válido a la compra.");
        }
        Proveedor proveedor = proveedorService.getEntityById(dto.getProveedorId());

        if (dto.getDetalles() == null || dto.getDetalles().isEmpty()) {
            throw new ReglaNegocioException("La compra debe contener al menos un detalle de producto.");
        }

        Compra compra = new Compra();
        compra.setProveedor(proveedor);
        if (dto.getFecha() != null) {
            compra.setFecha(dto.getFecha());
        }

        for (DetalleCompraDTO detalleDTO : dto.getDetalles()) {
            if (detalleDTO.getCantidad() == null || detalleDTO.getCantidad() <= 0) {
                throw new ReglaNegocioException("La cantidad de cada detalle debe ser mayor a 0.");
            }

            Producto productoBD = productoService.getEntityById(detalleDTO.getProductoId());

            productoService.actualizarStock(productoBD.getId(), detalleDTO.getCantidad());

            DetalleCompra detalle = new DetalleCompra();
            detalle.setProducto(productoBD);
            detalle.setCantidad(detalleDTO.getCantidad());
            detalle.setPrecioCompra(detalleDTO.getPrecioCompra());
            detalle.setCompra(compra);
            compra.getDetalles().add(detalle);
        }

        Compra guardada = repository.save(compra);
        return toDTO(guardada);
    }

    @Override
    public CompraDTO update(Long id, CompraDTO dto) {
        Compra existente = repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Compra con ID " + id + " no encontrada."));

        if (dto.getProveedorId() != null) {
            Proveedor proveedor = proveedorService.getEntityById(dto.getProveedorId());
            existente.setProveedor(proveedor);
        }
        if (dto.getFecha() != null) {
            existente.setFecha(dto.getFecha());
        }

        return toDTO(repository.save(existente));
    }
}

