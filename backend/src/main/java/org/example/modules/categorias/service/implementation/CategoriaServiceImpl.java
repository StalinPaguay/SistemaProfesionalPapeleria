package org.example.modules.categorias.service.implementation;

import org.example.common.service.BaseServiceImpl;

import org.example.modules.categorias.dto.CategoriaDTO;
import org.example.modules.categorias.entity.Categoria;
import org.example.exception.RecursoNoEncontradoException;
import org.example.modules.categorias.mapper.CategoriaMapper;
import org.example.modules.categorias.repository.CategoriaRepository;
import org.example.modules.categorias.service.interfaces.CategoriaService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
/**
 * Implementación del servicio de Categoria.
 */
public class CategoriaServiceImpl extends BaseServiceImpl<Categoria, CategoriaDTO, Long> implements CategoriaService {

    private final CategoriaRepository repository;

    public CategoriaServiceImpl(CategoriaRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<Categoria, Long> getRepository() {
        return repository;
    }

    @Override
    protected CategoriaDTO toDTO(Categoria entity) {
        return CategoriaMapper.toDTO(entity);
    }

    @Override
    protected Categoria toEntity(CategoriaDTO dto) {
        return CategoriaMapper.toEntity(dto);
    }

    @Override
    public CategoriaDTO update(Long id, CategoriaDTO dto) {
        Categoria existente = repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Categoría con ID " + id + " no encontrada."));
        existente.setNombre(dto.getNombre());
        Categoria actualizada = repository.save(existente);
        return toDTO(actualizada);
    }
}

