package org.example.modules.detallecompra.service.implementation;

import org.example.common.service.BaseServiceImpl;

import org.example.modules.detallecompra.dto.DetalleCompraDTO;
import org.example.modules.detallecompra.entity.DetalleCompra;
import org.example.modules.productos.entity.Producto;
import org.example.exception.RecursoNoEncontradoException;
import org.example.exception.ReglaNegocioException;
import org.example.modules.detallecompra.mapper.DetalleCompraMapper;
import org.example.modules.detallecompra.repository.DetalleCompraRepository;
import org.example.modules.productos.repository.ProductoRepository;
import org.example.modules.detallecompra.service.interfaces.DetalleCompraService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
/**
 * Implementación del servicio de DetalleCompra.
 */
public class DetalleCompraServiceImpl extends BaseServiceImpl<DetalleCompra, DetalleCompraDTO, Long> implements DetalleCompraService {

    private final DetalleCompraRepository repository;
    private final ProductoRepository productoRepository;

    public DetalleCompraServiceImpl(DetalleCompraRepository repository, ProductoRepository productoRepository) {
        this.repository = repository;
        this.productoRepository = productoRepository;
    }

    @Override
    protected JpaRepository<DetalleCompra, Long> getRepository() {
        return repository;
    }

    @Override
    protected DetalleCompraDTO toDTO(DetalleCompra entity) {
        return DetalleCompraMapper.toDTO(entity);
    }

    @Override
    protected DetalleCompra toEntity(DetalleCompraDTO dto) {
        return DetalleCompraMapper.toEntity(dto);
    }

    @Override
    @Transactional
    /**
     * Guarda o crea un registro.
     * @param dto el objeto a guardar
     * @return el objeto guardado
     */
    public DetalleCompraDTO save(DetalleCompraDTO dto) {
        if (dto.getCantidad() == null || dto.getCantidad() <= 0) {
            throw new ReglaNegocioException("La cantidad debe ser mayor a 0.");
        }

        Producto productoBD = productoRepository.findById(dto.getProductoId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto con ID " + dto.getProductoId() + " no encontrado."));

        productoBD.setStock(productoBD.getStock() + dto.getCantidad());
        productoRepository.save(productoBD);

        DetalleCompra entity = new DetalleCompra();
        entity.setCantidad(dto.getCantidad());
        entity.setPrecioCompra(dto.getPrecioCompra());
        entity.setProducto(productoBD);

        DetalleCompra guardado = repository.save(entity);
        return toDTO(guardado);
    }

    @Override
    public DetalleCompraDTO update(Long id, DetalleCompraDTO dto) {
        DetalleCompra existente = repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Detalle de compra con ID " + id + " no encontrado."));

        if (dto.getCantidad() != null) {
            if (dto.getCantidad() <= 0) {
                throw new ReglaNegocioException("La cantidad debe ser mayor a 0.");
            }
            existente.setCantidad(dto.getCantidad());
        }
        if (dto.getPrecioCompra() != null) {
            existente.setPrecioCompra(dto.getPrecioCompra());
        }

        return toDTO(repository.save(existente));
    }
}
