package org.example.modules.ventas.dto;

import jakarta.validation.constraints.*;

import org.example.modules.ventas.entity.Venta;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Objeto de Transferencia de Datos (DTO) para Venta.
 */
public class VentaDTO {
    private java.time.LocalDateTime fechaVenta;
    private String clienteNombre;
    private Long vendedorId;


    private Long id;
    private Long clienteId;
    @NotBlank(message = "El campo vendedorNombre es obligatorio")
    private String vendedorNombre;

    @NotNull(message = "El campo total es obligatorio")

    private BigDecimal total;
    
    private List<DetalleVentaDTO> detalles;

    /**

     * Obtiene el valor.

     * @return el valor

     */

    public Long getId() { return id; }
    /**
     * Establece el valor.
     * @param valor el valor a establecer
     */
    public void setId(Long id) { this.id = id; }
    /**
     * Obtiene el valor.
     * @return el valor
     */
    public java.time.LocalDateTime getFechaVenta() { return fechaVenta; }
    /**
     * Establece el valor.
     * @param valor el valor a establecer
     */
    public void setFechaVenta(java.time.LocalDateTime fechaVenta) { this.fechaVenta = fechaVenta; }
    /**
     * Obtiene el valor.
     * @return el valor
     */
    public Long getClienteId() { return clienteId; }
    /**
     * Establece el valor.
     * @param valor el valor a establecer
     */
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    /**
     * Obtiene el valor.
     * @return el valor
     */
    public String getClienteNombre() { return clienteNombre; }
    /**
     * Establece el valor.
     * @param valor el valor a establecer
     */
    public void setClienteNombre(String clienteNombre) { this.clienteNombre = clienteNombre; }
    /**
     * Obtiene el valor.
     * @return el valor
     */
    public Long getVendedorId() { return vendedorId; }
    /**
     * Establece el valor.
     * @param valor el valor a establecer
     */
    public void setVendedorId(Long vendedorId) { this.vendedorId = vendedorId; }
    /**
     * Obtiene el valor.
     * @return el valor
     */
    public String getVendedorNombre() { return vendedorNombre; }
    /**
     * Establece el valor.
     * @param valor el valor a establecer
     */
    public void setVendedorNombre(String vendedorNombre) { this.vendedorNombre = vendedorNombre; }
    /**
     * Obtiene el valor.
     * @return el valor
     */
    public BigDecimal getTotal() { return total; }
    /**
     * Establece el valor.
     * @param valor el valor a establecer
     */
    public void setTotal(BigDecimal total) { this.total = total; }
    /**
     * Obtiene el valor.
     * @return el valor
     */
    public List<DetalleVentaDTO> getDetalles() { return detalles; }
    /**
     * Establece el valor.
     * @param valor el valor a establecer
     */
    public void setDetalles(List<DetalleVentaDTO> detalles) { this.detalles = detalles; }
}
