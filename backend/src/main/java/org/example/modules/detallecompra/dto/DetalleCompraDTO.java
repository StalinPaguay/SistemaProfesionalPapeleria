package org.example.modules.detallecompra.dto;
import jakarta.validation.constraints.NotBlank;

import org.example.modules.detallecompra.entity.DetalleCompra;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Objeto de Transferencia de Datos (DTO) para DetalleCompra.
 */
public class DetalleCompraDTO {

    private Long id;

    @NotNull(message = "El campo productoId es obligatorio")
    private Long productoId;

    // Campo de solo lectura para respuestas
    @NotBlank(message = "El campo productoNombre es obligatorio")
    private String productoNombre;

    @NotNull(message = "El campo cantidad es obligatorio")
    private Integer cantidad;

    @NotNull(message = "El campo precioCompra es obligatorio")
    private BigDecimal precioCompra;

    public DetalleCompraDTO() {}

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
    public BigDecimal getPrecioCompra() { return precioCompra; }
    /**
     * Establece el valor.
     * @param valor el valor a establecer
     */
    public void setPrecioCompra(BigDecimal precioCompra) { this.precioCompra = precioCompra; }
}
