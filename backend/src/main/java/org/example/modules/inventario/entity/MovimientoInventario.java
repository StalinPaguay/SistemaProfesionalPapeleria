package org.example.modules.inventario.entity;
import jakarta.validation.constraints.NotBlank;

import org.example.modules.productos.entity.Producto;

import org.example.modules.auth.entity.Usuario;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "movimientos_inventario")
/**
 * Entidad que representa a MovimientoInventario.
 */
public class MovimientoInventario {
    @jakarta.persistence.Enumerated(jakarta.persistence.EnumType.STRING)
    private TipoMovimiento tipoMovimiento;
    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser mayor a 0")
    private Integer cantidad;
    private Integer stockPrevio;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @NotNull(message = "El campo fechaMovimiento es obligatorio")
    private LocalDateTime fechaMovimiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario responsable;

    @NotBlank(message = "El campo motivo es obligatorio")

    private String motivo;

    @Column(name = "stock_actual")
    @NotNull(message = "El campo stockActual es obligatorio")
    private Integer stockActual;

    @PrePersist
    /**
     * Método prePersist.
     */
    public void prePersist() {
        if (this.fechaMovimiento == null) {
            this.fechaMovimiento = LocalDateTime.now();
        }
    }

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
    public TipoMovimiento getTipoMovimiento() { return tipoMovimiento; }
    /**
     * Establece el valor.
     * @param valor el valor a establecer
     */
    public void setTipoMovimiento(TipoMovimiento tipoMovimiento) { this.tipoMovimiento = tipoMovimiento; }
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
    public LocalDateTime getFechaMovimiento() { return fechaMovimiento; }
    /**
     * Establece el valor.
     * @param valor el valor a establecer
     */
    public void setFechaMovimiento(LocalDateTime fechaMovimiento) { this.fechaMovimiento = fechaMovimiento; }
    /**
     * Obtiene el valor.
     * @return el valor
     */
    public Usuario getResponsable() { return responsable; }
    /**
     * Establece el valor.
     * @param valor el valor a establecer
     */
    public void setResponsable(Usuario responsable) { this.responsable = responsable; }
    /**
     * Obtiene el valor.
     * @return el valor
     */
    public String getMotivo() { return motivo; }
    /**
     * Establece el valor.
     * @param valor el valor a establecer
     */
    public void setMotivo(String motivo) { this.motivo = motivo; }
    /**
     * Obtiene el valor.
     * @return el valor
     */
    public Integer getStockPrevio() { return stockPrevio; }
    /**
     * Establece el valor.
     * @param valor el valor a establecer
     */
    public void setStockPrevio(Integer stockPrevio) { this.stockPrevio = stockPrevio; }
    /**
     * Obtiene el valor.
     * @return el valor
     */
    public Integer getStockActual() { return stockActual; }
    /**
     * Establece el valor.
     * @param valor el valor a establecer
     */
    public void setStockActual(Integer stockActual) { this.stockActual = stockActual; }
}
