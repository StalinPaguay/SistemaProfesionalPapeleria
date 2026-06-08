package org.example.common.service;

import org.example.exception.RecursoNoEncontradoException;
import org.example.common.service.BaseService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseServiceImpl<E, DTO, ID> implements BaseService<DTO, ID> {

    protected abstract JpaRepository<E, ID> getRepository();
    protected abstract DTO toDTO(E entity);
    protected abstract E toEntity(DTO dto);

    @Override
    public List<DTO> findAll() {
        return getRepository().findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DTO findById(ID id) {
        E entity = getRepository().findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Recurso con ID " + id + " no encontrado."));
        return toDTO(entity);
    }

    @Override
    public DTO save(DTO dto) {
        E entity = toEntity(dto);
        E saved = getRepository().save(entity);
        return toDTO(saved);
    }

    @Override
    public void delete(ID id) {
        if (!getRepository().existsById(id)) {
            throw new RecursoNoEncontradoException("Recurso con ID " + id + " no encontrado.");
        }
        getRepository().deleteById(id);
    }
}

