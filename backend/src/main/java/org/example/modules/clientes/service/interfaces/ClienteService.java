package org.example.modules.clientes.service.interfaces;

import org.example.common.service.BaseService;

import org.example.modules.clientes.dto.ClienteDTO;

import org.example.modules.clientes.entity.Cliente;

/**
 * Interfaz que define las operaciones para ClienteService.
 */
public interface ClienteService extends org.example.common.service.BaseService<ClienteDTO, Long> {
    Cliente getEntityById(Long id);
}

