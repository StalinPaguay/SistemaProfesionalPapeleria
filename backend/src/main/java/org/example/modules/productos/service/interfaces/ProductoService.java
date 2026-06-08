package org.example.modules.productos.service.interfaces;

import org.example.common.service.BaseService;

import org.example.modules.productos.dto.ProductoDTO;

import org.example.modules.productos.entity.Producto;

/**
 * Interfaz que define las operaciones para ProductoService.
 */
public interface ProductoService extends org.example.common.service.BaseService<ProductoDTO, Long> {
    java.util.List<ProductoDTO> obtenerBajoStock();
    java.util.List<ProductoDTO> obtenerAgotados();
    Producto getEntityById(Long id);
    void actualizarStock(Long id, Integer cantidadAgregar);
}
