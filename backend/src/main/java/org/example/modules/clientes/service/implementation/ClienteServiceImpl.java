package org.example.modules.clientes.service.implementation;

import org.example.modules.clientes.dto.ClienteDTO;
import org.example.modules.clientes.entity.Cliente;
import org.example.exception.RecursoNoEncontradoException;
import org.example.modules.clientes.mapper.ClienteMapper;
import org.example.modules.clientes.repository.ClienteRepository;
import org.example.modules.clientes.service.interfaces.ClienteService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import org.example.common.service.BaseServiceImpl;

@Service
/**
 * Implementación del servicio de Cliente.
 */
public class ClienteServiceImpl extends BaseServiceImpl<Cliente, ClienteDTO, Long> implements ClienteService {

    private final ClienteRepository repository;

    public ClienteServiceImpl(ClienteRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<Cliente, Long> getRepository() {
        return repository;
    }

    @Override
    protected ClienteDTO toDTO(Cliente entity) {
        return ClienteMapper.toDTO(entity);
    }

    @Override
    protected Cliente toEntity(ClienteDTO dto) {
        return ClienteMapper.toEntity(dto);
    }

    @Override
    public ClienteDTO update(Long id, ClienteDTO dto) {
        Cliente existente = repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente con ID " + id + " no encontrado."));
        
        existente.setNombre(dto.getNombre());
        existente.setApellido(dto.getApellido());
        existente.setTelefono(dto.getTelefono());
        existente.setCorreo(dto.getCorreo());
        existente.setDireccion(dto.getDireccion());
        
        return toDTO(repository.save(existente));
    }

    @Override
    public Cliente getEntityById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente con ID " + id + " no encontrado."));
    }
}

