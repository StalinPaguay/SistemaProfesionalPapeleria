package org.example.modules.compras.mapper;

import org.example.modules.compras.dto.CompraDTO;
import org.example.modules.detallecompra.dto.DetalleCompraDTO;
import org.example.modules.compras.entity.Compra;
import org.example.modules.detallecompra.mapper.DetalleCompraMapper;

import java.util.List;
import java.util.stream.Collectors;

public class CompraMapper {

    public static CompraDTO toDTO(Compra entity) {
        if (entity == null) return null;
        CompraDTO dto = new CompraDTO();
        dto.setId(entity.getId());
        dto.setFecha(entity.getFecha());
        if (entity.getProveedor() != null) {
            dto.setProveedorId(entity.getProveedor().getId());
            dto.setProveedorNombre(entity.getProveedor().getNombre());
        }
        if (entity.getDetalles() != null) {
            List<DetalleCompraDTO> detallesDTO = entity.getDetalles().stream()
                    .map(DetalleCompraMapper::toDTO)
                    .collect(Collectors.toList());
            dto.setDetalles(detallesDTO);
        }
        return dto;
    }

    public static Compra toEntity(CompraDTO dto) {
        if (dto == null) return null;
        Compra entity = new Compra();
        entity.setId(dto.getId());
        entity.setFecha(dto.getFecha());
        
        // Proveedor y detalles se asignan en el servicio
        return entity;
    }
}

