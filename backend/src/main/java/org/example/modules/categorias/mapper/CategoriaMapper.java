package org.example.modules.categorias.mapper;

import org.example.modules.categorias.dto.CategoriaDTO;
import org.example.modules.categorias.entity.Categoria;

/**
 * Entidad que representa a CategoriaMapper.
 */
public class CategoriaMapper {

    // Convierte entidad a DTO
    /**
     * Método toDTO.
     */
    public static CategoriaDTO toDTO(Categoria entity) {
        if (entity == null) return null;
        CategoriaDTO dto = new CategoriaDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        return dto;
    }

    // Convierte DTO a entidad
    /**
     * Método toEntity.
     */
    public static Categoria toEntity(CategoriaDTO dto) {
        if (dto == null) return null;
        Categoria entity = new Categoria();
        entity.setId(dto.getId());
        entity.setNombre(dto.getNombre());
        return entity;
    }
}

