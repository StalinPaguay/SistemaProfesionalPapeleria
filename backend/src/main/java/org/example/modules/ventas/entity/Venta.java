package org.example.modules.ventas.entity;

import jakarta.validation.constraints.*;

import org.example.modules.clientes.entity.Cliente;

import org.example.modules.auth.entity.Usuario;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ventas")
/**
 * Entidad que representa a Venta.
 */
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_venta", nullable = false)
    @NotNull(message = "El campo fechaVenta es obligatorio")
    private LocalDateTime fechaVenta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendedor_id", nullable = false)
    private Usuario vendedor;

    @Column(nullable = false)
    private BigDecimal total = BigDecimal.ZERO;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DetalleVenta> detalles = new ArrayList<>();

    @PrePersist
    /**
     * Método prePersist.
     */
    public void prePersist() {
        if (this.fechaVenta == null) {
            this.fechaVenta = LocalDateTime.now();
        }
    }

    public Venta() {}

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
    public LocalDateTime getFechaVenta() { return fechaVenta; }
    /**
     * Establece el valor.
     * @param valor el valor a establecer
     */
    public void setFechaVenta(LocalDateTime fechaVenta) { this.fechaVenta = fechaVenta; }
    /**
     * Obtiene el valor.
     * @return el valor
     */
    public Cliente getCliente() { return cliente; }
    /**
     * Establece el valor.
     * @param valor el valor a establecer
     */
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    /**
     * Obtiene el valor.
     * @return el valor
     */
    public Usuario getVendedor() { return vendedor; }
    /**
     * Establece el valor.
     * @param valor el valor a establecer
     */
    public void setVendedor(Usuario vendedor) { this.vendedor = vendedor; }
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
    public List<DetalleVenta> getDetalles() { return detalles; }
    /**
     * Establece el valor.
     * @param valor el valor a establecer
     */
    public void setDetalles(List<DetalleVenta> detalles) { this.detalles = detalles; }
}
