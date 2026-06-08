package org.example.modules.ventas.mapper;

import org.example.modules.ventas.dto.DetalleVentaDTO;
import org.example.modules.ventas.dto.VentaDTO;
import org.example.modules.ventas.entity.DetalleVenta;
import org.example.modules.ventas.entity.Venta;

import java.util.stream.Collectors;

/**
 * Entidad que representa a VentaMapper.
 */
public class VentaMapper {

    /**

     * Método toDTO.

     */

    public static VentaDTO toDTO(Venta entity) {
        if (entity == null) return null;
        VentaDTO dto = new VentaDTO();
        dto.setId(entity.getId());
        dto.setFechaVenta(entity.getFechaVenta());
        dto.setTotal(entity.getTotal());

        if (entity.getCliente() != null) {
            dto.setClienteId(entity.getCliente().getId());
            dto.setClienteNombre(entity.getCliente().getNombre() + " " + entity.getCliente().getApellido());
        }

        if (entity.getVendedor() != null) {
            dto.setVendedorId(entity.getVendedor().getId());
            dto.setVendedorNombre(entity.getVendedor().getNombre() + " " + entity.getVendedor().getApellido());
        }

        if (entity.getDetalles() != null) {
            dto.setDetalles(entity.getDetalles().stream().map(VentaMapper::detalleToDTO).collect(Collectors.toList()));
        }
        return dto;
    }

    /**

     * Método toEntity.

     */

    public static Venta toEntity(VentaDTO dto) {
        if (dto == null) return null;
        Venta entity = new Venta();
        entity.setId(dto.getId());
        entity.setFechaVenta(dto.getFechaVenta());
        // El cliente y el vendedor se asignarán en el servicio
        return entity;
    }

    /**

     * Método detalleToDTO.

     */

    public static DetalleVentaDTO detalleToDTO(DetalleVenta entity) {
        if (entity == null) return null;
        DetalleVentaDTO dto = new DetalleVentaDTO();
        dto.setId(entity.getId());
        dto.setCantidad(entity.getCantidad());
        dto.setPrecioUnitario(entity.getPrecioUnitario());
        dto.setSubtotal(entity.getSubtotal());

        if (entity.getProducto() != null) {
            dto.setProductoId(entity.getProducto().getId());
            dto.setProductoNombre(entity.getProducto().getNombre());
        }
        return dto;
    }

    /**

     * Método detalleToEntity.

     */

    public static DetalleVenta detalleToEntity(DetalleVentaDTO dto) {
        if (dto == null) return null;
        DetalleVenta entity = new DetalleVenta();
        entity.setId(dto.getId());
        entity.setCantidad(dto.getCantidad());
        // El precio y producto se asignarán en el servicio para evitar alteraciones desde el frontend
        return entity;
    }
}
