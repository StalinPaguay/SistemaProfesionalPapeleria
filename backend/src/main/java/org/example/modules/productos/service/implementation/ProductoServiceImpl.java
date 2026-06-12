package org.example.modules.productos.service.implementation;

import org.example.modules.productos.dto.ProductoDTO;
import org.example.modules.categorias.entity.Categoria;
import org.example.modules.productos.entity.Producto;
import org.example.exception.RecursoNoEncontradoException;
import org.example.exception.ReglaNegocioException;
import org.example.modules.productos.mapper.ProductoMapper;
import org.example.modules.categorias.repository.CategoriaRepository;
import org.example.modules.productos.repository.ProductoRepository;
import org.example.modules.productos.service.interfaces.ProductoService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import org.example.common.service.BaseServiceImpl;
@Service
/**
 * Implementación del servicio de Producto.
 */
public class ProductoServiceImpl extends BaseServiceImpl<Producto, ProductoDTO, Long> implements ProductoService {

    private final ProductoRepository repository;
    private final CategoriaRepository categoriaRepository;

    public ProductoServiceImpl(ProductoRepository repository, CategoriaRepository categoriaRepository) {
        this.repository = repository;
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    protected JpaRepository<Producto, Long> getRepository() {
        return repository;
    }

    @Override
    protected ProductoDTO toDTO(Producto entity) {
        return ProductoMapper.toDTO(entity);
    }

    @Override
    protected Producto toEntity(ProductoDTO dto) {
        return ProductoMapper.toEntity(dto);
    }

    @Override
    public ProductoDTO save(ProductoDTO dto) {
        validarReglasNegocio(dto);
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Categoría con ID " + dto.getCategoriaId() + " no encontrada."));

        Producto entity = toEntity(dto);
        entity.setCategoria(categoria);
        if (entity.getStock() <= entity.getStockMinimo()) {
            entity.setAlertaStockBajo(true);
        } else {
            entity.setAlertaStockBajo(false);
        }
        return toDTO(repository.save(entity));
    }

    @Override
    public ProductoDTO update(Long id, ProductoDTO dto) {
        Producto existente = repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto con ID " + id + " no encontrado."));

        validarReglasNegocio(dto);
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Categoría con ID " + dto.getCategoriaId() + " no encontrada."));

        existente.setNombre(dto.getNombre());
        existente.setPrecio(dto.getPrecio());
        existente.setStock(dto.getStock());
        existente.setCategoria(categoria);
        if (dto.getDescripcion() != null) existente.setDescripcion(dto.getDescripcion());
        if (dto.getImagenUrl() != null) existente.setImagenUrl(dto.getImagenUrl());
        if (existente.getStock() <= existente.getStockMinimo()) {
            existente.setAlertaStockBajo(true);
        } else {
            existente.setAlertaStockBajo(false);
        }
        return toDTO(repository.save(existente));
    }

    private void validarReglasNegocio(ProductoDTO dto) {
        if (dto.getPrecio() == null || dto.getPrecio().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ReglaNegocioException("El precio debe ser mayor a 0.");
        }
        if (dto.getStock() == null || dto.getStock() < 0) {
            throw new ReglaNegocioException("El stock no puede ser negativo.");
        }
    }

    @Override
    public List<ProductoDTO> obtenerBajoStock() {
        return findAll().stream()
                .filter(p -> p.getStock() <= p.getStockMinimo() && p.getStock() > 0)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductoDTO> obtenerAgotados() {
        return findAll().stream()
                .filter(p -> p.getStock() == 0)
                .collect(Collectors.toList());
    }

    @Override
    public Producto getEntityById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto con ID " + id + " no encontrado."));
    }

    @Override
    public void actualizarStock(Long id, Integer cantidadAgregar) {
        Producto producto = getEntityById(id);
        int nuevoStock = producto.getStock() + cantidadAgregar;
        if (nuevoStock < 0) {
            throw new ReglaNegocioException("Stock insuficiente para el producto: " + producto.getNombre());
        }
        producto.setStock(nuevoStock);
        
        if (nuevoStock <= producto.getStockMinimo()) {
            producto.setAlertaStockBajo(true);
            System.out.println("ALERTA: Stock bajo para el producto " + producto.getNombre() + " (Stock actual: " + nuevoStock + ")");
        } else {
            producto.setAlertaStockBajo(false);
        }
        
        repository.save(producto);
    }
}
