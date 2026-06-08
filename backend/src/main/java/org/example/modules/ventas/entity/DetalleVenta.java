package org.example.modules.ventas.entity;

import org.example.modules.productos.entity.Producto;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "detalles_venta")
/**
 * Entidad que representa a DetalleVenta.
 */
public class DetalleVenta {
    @jakarta.validation.constraints.NotNull(message = "El precio es obligatorio")
    private java.math.BigDecimal precioUnitario;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venta_id", nullable = false)
    private Venta venta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @NotNull(message = "El campo cantidad es obligatorio")
    private Integer cantidad;

    @Column(name = "subtotal", nullable = false)
    @NotNull(message = "El campo subtotal es obligatorio")
    private BigDecimal subtotal;

    public DetalleVenta() {}

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
    public Venta getVenta() { return venta; }
    /**
     * Establece el valor.
     * @param valor el valor a establecer
     */
    public void setVenta(Venta venta) { this.venta = venta; }
    /**
     * Obtiene el valor.
     * @return el valor
     */
    public Producto getProducto() { return producto; }
    /**
     * Establece el valor.
     * @param valor el valor a establecer
     */
    public void setProducto(Producto producto) { this.producto = producto; }
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
