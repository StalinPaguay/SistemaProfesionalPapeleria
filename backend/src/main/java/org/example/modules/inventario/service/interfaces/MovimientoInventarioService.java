package org.example.modules.inventario.service.interfaces;

import org.example.common.service.BaseService;

import org.example.modules.productos.entity.Producto;

import org.example.modules.auth.entity.Usuario;

import org.example.modules.inventario.dto.MovimientoInventarioDTO;
import java.util.List;

/**
 * Interfaz que define las operaciones para MovimientoInventarioService.
 */
public interface MovimientoInventarioService extends org.example.common.service.BaseService<MovimientoInventarioDTO, Long> {
    List<MovimientoInventarioDTO> buscarPorProducto(Long productoId);
    void registrarMovimiento(org.example.modules.productos.entity.Producto producto, 
                             org.example.modules.inventario.entity.TipoMovimiento tipo, 
                             Integer cantidad, 
                             Integer stockAnterior, 
                             Integer stockNuevo, 
                             org.example.modules.auth.entity.Usuario responsable, 
                             String motivo);
}
