package org.example.modules.inventario.mapper;

import org.example.modules.productos.entity.Producto;

import org.example.modules.auth.entity.Usuario;

import org.example.modules.inventario.dto.MovimientoInventarioDTO;
import org.example.modules.inventario.entity.MovimientoInventario;

/**
 * Entidad que representa a MovimientoInventarioMapper.
 */
public class MovimientoInventarioMapper {

    /**

     * Método toDTO.

     */

    public static MovimientoInventarioDTO toDTO(MovimientoInventario entity) {
        if (entity == null) return null;
        MovimientoInventarioDTO dto = new MovimientoInventarioDTO();
        dto.setId(entity.getId());
        
        if (entity.getProducto() != null) {
            dto.setProductoId(entity.getProducto().getId());
            dto.setProductoNombre(entity.getProducto().getNombre());
        }
        
        dto.setTipoMovimiento(entity.getTipoMovimiento());
        dto.setCantidad(entity.getCantidad());
        dto.setFechaMovimiento(entity.getFechaMovimiento());
        
        if (entity.getResponsable() != null) {
            dto.setResponsableId(entity.getResponsable().getId());
            dto.setResponsableNombre(entity.getResponsable().getNombre() + " " + entity.getResponsable().getApellido());
        }
        
        dto.setMotivo(entity.getMotivo());
        dto.setStockPrevio(entity.getStockPrevio());
        dto.setStockActual(entity.getStockActual());
        
        return dto;
    }

    /**

     * Método toEntity.

     */

    public static MovimientoInventario toEntity(MovimientoInventarioDTO dto) {
        if (dto == null) return null;
        MovimientoInventario entity = new MovimientoInventario();
        entity.setId(dto.getId());
        entity.setTipoMovimiento(dto.getTipoMovimiento());
        entity.setCantidad(dto.getCantidad());
        entity.setMotivo(dto.getMotivo());
        // Producto y Usuario se resuelven en el service
        return entity;
    }
}
