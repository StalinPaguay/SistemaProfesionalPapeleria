package org.example.modules.detallecompra.mapper;

import org.example.modules.detallecompra.dto.DetalleCompraDTO;
import org.example.modules.detallecompra.entity.DetalleCompra;

public class DetalleCompraMapper {

    public static DetalleCompraDTO toDTO(DetalleCompra entity) {
        if (entity == null) return null;
        DetalleCompraDTO dto = new DetalleCompraDTO();
        dto.setId(entity.getId());
        dto.setCantidad(entity.getCantidad());
        dto.setPrecioCompra(entity.getPrecioCompra());
        if (entity.getProducto() != null) {
            dto.setProductoId(entity.getProducto().getId());
            dto.setProductoNombre(entity.getProducto().getNombre());
        }
        return dto;
    }

    public static DetalleCompra toEntity(DetalleCompraDTO dto) {
        if (dto == null) return null;
        DetalleCompra entity = new DetalleCompra();
        entity.setId(dto.getId());
        entity.setCantidad(dto.getCantidad());
        entity.setPrecioCompra(dto.getPrecioCompra());
        return entity;
    }
}
