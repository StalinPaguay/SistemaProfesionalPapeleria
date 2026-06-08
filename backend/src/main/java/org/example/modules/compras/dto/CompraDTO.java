package org.example.modules.compras.dto;
import jakarta.validation.constraints.NotBlank;

import org.example.modules.compras.entity.Compra;
import org.example.modules.detallecompra.dto.DetalleCompraDTO;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

/**
 * Objeto de Transferencia de Datos (DTO) para Compra.
 */
public class CompraDTO {
    private java.time.LocalDate fecha;


    private Long id;

    @NotNull(message = "El proveedor es obligatorio")
    @NotNull(message = "El campo proveedorId es obligatorio")
    private Long proveedorId;

    // Campo de solo lectura para respuestas
    @NotBlank(message = "El campo proveedorNombre es obligatorio")
    private String proveedorNombre;

    private List<DetalleCompraDTO> detalles;

    public CompraDTO() {}

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
    public Long getProveedorId() { return proveedorId; }
    /**
     * Establece el valor.
     * @param valor el valor a establecer
     */
    public void setProveedorId(Long proveedorId) { this.proveedorId = proveedorId; }
    /**
     * Obtiene el valor.
     * @return el valor
     */
    public String getProveedorNombre() { return proveedorNombre; }
    /**
     * Establece el valor.
     * @param valor el valor a establecer
     */
    public void setProveedorNombre(String proveedorNombre) { this.proveedorNombre = proveedorNombre; }
    /**
     * Obtiene el valor.
     * @return el valor
     */
    public List<DetalleCompraDTO> getDetalles() { return detalles; }
    /**
     * Establece el valor.
     * @param valor el valor a establecer
     */
    public void setDetalles(List<DetalleCompraDTO> detalles) { this.detalles = detalles; }
}

