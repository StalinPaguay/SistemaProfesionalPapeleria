package org.example.modules.compras.entity;

import jakarta.validation.constraints.*;

import org.example.modules.proveedores.entity.Proveedor;

import org.example.modules.detallecompra.entity.DetalleCompra;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "compras")
/**
 * Entidad que representa a Compra.
 */
public class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El campo fecha es obligatorio")

    private LocalDate fecha;

    @ManyToOne(fetch = jakarta.persistence.FetchType.LAZY)
    @JoinColumn(name = "proveedor_id", nullable = false)
    private Proveedor proveedor;

    // Relación uno a muchos: Una compra tiene muchos detalles.
    // CascadeType.ALL hace que si guardamos la Compra, automáticamente se guarden sus detalles.
    @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleCompra> detalles = new ArrayList<>();

    public Compra() {
        this.fecha = LocalDate.now();
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
    public LocalDate getFecha() { return fecha; }
    /**
     * Establece el valor.
     * @param valor el valor a establecer
     */
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    /**
     * Obtiene el valor.
     * @return el valor
     */
    public Proveedor getProveedor() { return proveedor; }
    /**
     * Establece el valor.
     * @param valor el valor a establecer
     */
    public void setProveedor(Proveedor proveedor) { this.proveedor = proveedor; }
    /**
     * Obtiene el valor.
     * @return el valor
     */
    public List<DetalleCompra> getDetalles() { return detalles; }
    /**
     * Establece el valor.
     * @param valor el valor a establecer
     */
    public void setDetalles(List<DetalleCompra> detalles) { this.detalles = detalles; }
}

