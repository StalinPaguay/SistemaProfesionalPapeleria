package org.example.modules.productos.mapper;

import org.example.modules.productos.dto.ProductoDTO;
import org.example.modules.categorias.entity.Categoria;
import org.example.modules.productos.entity.Producto;

/**
 * Entidad que representa a ProductoMapper.
 */
public class ProductoMapper {

    // Convierte entidad a DTO
    /**
     * Método toDTO.
     */
    public static ProductoDTO toDTO(Producto entity) {
        if (entity == null) return null;
        ProductoDTO dto = new ProductoDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setPrecio(entity.getPrecio());
        dto.setStock(entity.getStock());
        dto.setStockMinimo(entity.getStockMinimo());
        dto.setStockMaximo(entity.getStockMaximo());
        if (entity.getCategoria() != null) {
            dto.setCategoriaId(entity.getCategoria().getId());
            dto.setCategoriaNombre(entity.getCategoria().getNombre());
        }
        return dto;
    }

    // Convierte DTO a entidad (la categoría se asigna en el Service)
    /**
     * Método toEntity.
     */
    public static Producto toEntity(ProductoDTO dto) {
        if (dto == null) return null;
        Producto entity = new Producto();
        entity.setId(dto.getId());
        entity.setNombre(dto.getNombre());
        entity.setPrecio(dto.getPrecio());
        entity.setStock(dto.getStock());
        if (dto.getStockMinimo() != null) entity.setStockMinimo(dto.getStockMinimo());
        if (dto.getStockMaximo() != null) entity.setStockMaximo(dto.getStockMaximo());
        // La relación con Categoria se resuelve en el Service
        return entity;
    }
}
