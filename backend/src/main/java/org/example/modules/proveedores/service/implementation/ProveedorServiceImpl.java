package org.example.modules.proveedores.service.implementation;

import org.example.modules.proveedores.dto.ProveedorDTO;
import org.example.modules.proveedores.entity.Proveedor;
import org.example.exception.RecursoNoEncontradoException;
import org.example.modules.proveedores.mapper.ProveedorMapper;
import org.example.modules.proveedores.repository.ProveedorRepository;
import org.example.modules.proveedores.service.interfaces.ProveedorService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import org.example.common.service.BaseServiceImpl;

@Service
/**
 * Implementación del servicio de Proveedor.
 */
public class ProveedorServiceImpl extends BaseServiceImpl<Proveedor, ProveedorDTO, Long> implements ProveedorService {

    private final ProveedorRepository repository;

    public ProveedorServiceImpl(ProveedorRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<Proveedor, Long> getRepository() {
        return repository;
    }

    @Override
    protected ProveedorDTO toDTO(Proveedor entity) {
        return ProveedorMapper.toDTO(entity);
    }

    @Override
    protected Proveedor toEntity(ProveedorDTO dto) {
        return ProveedorMapper.toEntity(dto);
    }

    @Override
    public ProveedorDTO update(Long id, ProveedorDTO dto) {
        Proveedor existente = repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Proveedor con ID " + id + " no encontrado."));
        existente.setNombre(dto.getNombre());
        existente.setTelefono(dto.getTelefono());
        existente.setCorreo(dto.getCorreo());
        return toDTO(repository.save(existente));
    }

    @Override
    public Proveedor getEntityById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Proveedor con ID " + id + " no encontrado."));
    }
}
