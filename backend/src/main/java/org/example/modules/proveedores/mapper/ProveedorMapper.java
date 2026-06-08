package org.example.modules.proveedores.mapper;

import org.example.modules.proveedores.dto.ProveedorDTO;
import org.example.modules.proveedores.entity.Proveedor;

/**
 * Entidad que representa a ProveedorMapper.
 */
public class ProveedorMapper {

    // Convierte entidad a DTO
    /**
     * Método toDTO.
     */
    public static ProveedorDTO toDTO(Proveedor entity) {
        if (entity == null) return null;
        ProveedorDTO dto = new ProveedorDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setTelefono(entity.getTelefono());
        dto.setCorreo(entity.getCorreo());
        return dto;
    }

    // Convierte DTO a entidad
    /**
     * Método toEntity.
     */
    public static Proveedor toEntity(ProveedorDTO dto) {
        if (dto == null) return null;
        Proveedor entity = new Proveedor();
        entity.setId(dto.getId());
        entity.setNombre(dto.getNombre());
        entity.setTelefono(dto.getTelefono());
        entity.setCorreo(dto.getCorreo());
        return entity;
    }
}
