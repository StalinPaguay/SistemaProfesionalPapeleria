package org.example.modules.detallecompra.entity;

import jakarta.validation.constraints.*;

import org.example.modules.compras.entity.Compra;

import org.example.modules.productos.entity.Producto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "detalles_compra")
/**
 * Entidad que representa a DetalleCompra.
 */
public class DetalleCompra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El campo cantidad es obligatorio")
    @Column(nullable = false)
    private Integer cantidad;

    @Column(nullable = false)
    @NotNull(message = "El campo precioCompra es obligatorio")
    private BigDecimal precioCompra;

    // @JsonIgnore evita bucles infinitos cuando Spring convierte el objeto a JSON
    @ManyToOne(fetch = jakarta.persistence.FetchType.LAZY)
    @JoinColumn(name = "compra_id", nullable = false)
    @JsonIgnore
    private Compra compra;

    @ManyToOne(fetch = jakarta.persistence.FetchType.LAZY)
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    public DetalleCompra() {}

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
    /**
     * Obtiene el valor.
     * @return el valor
     */
    public Compra getCompra() { return compra; }
    /**
     * Establece el valor.
     * @param valor el valor a establecer
     */
    public void setCompra(Compra compra) { this.compra = compra; }
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
}
