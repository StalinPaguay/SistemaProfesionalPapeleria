package org.example.modules.ventas.dto;
import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Objeto de Transferencia de Datos (DTO) para DetalleVenta.
 */
public class DetalleVentaDTO {
    private java.math.BigDecimal precioUnitario;


    private Long id;

    @NotNull(message = "El campo productoId es obligatorio")
    private Long productoId;
    
    @NotBlank(message = "El campo productoNombre es obligatorio")
    
    private String productoNombre;

    @NotNull(message = "El campo cantidad es obligatorio")
    private Integer cantidad;

    @NotNull(message = "El campo subtotal es obligatorio")
    private BigDecimal subtotal;

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
    public Long getProductoId() { return productoId; }
    /**
     * Establece el valor.
     * @param valor el valor a establecer
     */
    public void setProductoId(Long productoId) { this.productoId = productoId; }
    /**
     * Obtiene el valor.
     * @return el valor
     */
    public String getProductoNombre() { return productoNombre; }
    /**
     * Establece el valor.
     * @param valor el valor a establecer
     */
    public void setProductoNombre(String productoNombre) { this.productoNombre = productoNombre; }
    /**
     * Obtiene el valor.
     * @return el valor
     */
    public Integer getCantidad() { return cantidad; }
    /**
     * Establece el valor.
     * @param valor el valor a establecer
     */
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    /**
     * Obtiene el valor.
     * @return el valor
     */
    public BigDecimal getPrecioUnitario() { return precioUnitario; }
    /**
     * Establece el valor.
     * @param valor el valor a establecer
     */
    public void setPrecioUnitario(BigDecimal precioUnitario) { this.precioUnitario = precioUnitario; }
    /**
     * Obtiene el valor.
     * @return el valor
     */
    public BigDecimal getSubtotal() { return subtotal; }
    /**
     * Establece el valor.
     * @param valor el valor a establecer
     */
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
}
