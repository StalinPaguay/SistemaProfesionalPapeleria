package org.example.modules.proveedores.service.interfaces;

import org.example.common.service.BaseService;

import org.example.modules.proveedores.dto.ProveedorDTO;

import org.example.modules.proveedores.entity.Proveedor;

/**
 * Interfaz que define las operaciones para ProveedorService.
 */
public interface ProveedorService extends org.example.common.service.BaseService<ProveedorDTO, Long> {
    Proveedor getEntityById(Long id);
}
