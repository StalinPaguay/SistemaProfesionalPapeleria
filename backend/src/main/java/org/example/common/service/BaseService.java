package org.example.common.service;

import java.util.List;

/**
 * Interfaz que define las operaciones para BaseService.
 */
public interface BaseService<T, ID> {
    List<T> findAll();
    T findById(ID id);
    T save(T entity);
    T update(ID id, T entity);
    void delete(ID id);
}

