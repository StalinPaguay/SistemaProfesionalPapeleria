package org.example.modules.clientes.mapper;

import org.example.modules.clientes.dto.ClienteDTO;
import org.example.modules.clientes.entity.Cliente;

/**
 * Entidad que representa a ClienteMapper.
 */
public class ClienteMapper {

    /**

     * Método toDTO.

     */

    public static ClienteDTO toDTO(Cliente entity) {
        if (entity == null) return null;
        ClienteDTO dto = new ClienteDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setApellido(entity.getApellido());
        dto.setTelefono(entity.getTelefono());
        dto.setCorreo(entity.getCorreo());
        dto.setDireccion(entity.getDireccion());
        return dto;
    }

    /**

     * Método toEntity.

     */

    public static Cliente toEntity(ClienteDTO dto) {
        if (dto == null) return null;
        Cliente entity = new Cliente();
        entity.setId(dto.getId());
        entity.setNombre(dto.getNombre());
        entity.setApellido(dto.getApellido());
        entity.setTelefono(dto.getTelefono());
        entity.setCorreo(dto.getCorreo());
        entity.setDireccion(dto.getDireccion());
        return entity;
    }
}

