package org.example.modules.proveedores.dto;

import org.example.modules.proveedores.entity.Proveedor;

import jakarta.validation.constraints.NotBlank;

/**
 * Objeto de Transferencia de Datos (DTO) para Proveedor.
 */
public class ProveedorDTO {

    private Long id;

    @NotBlank(message = "El campo nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El campo correo es obligatorio")
    private String correo;

    @NotBlank(message = "El campo telefono es obligatorio")
    private String telefono;

    public ProveedorDTO() {}

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
    public String getNombre() { return nombre; }
    /**
     * Establece el valor.
     * @param valor el valor a establecer
     */
    public void setNombre(String nombre) { this.nombre = nombre; }
    /**
     * Obtiene el valor.
     * @return el valor
     */
    public String getTelefono() { return telefono; }
    /**
     * Establece el valor.
     * @param valor el valor a establecer
     */
    public void setTelefono(String telefono) { this.telefono = telefono; }
    /**
     * Obtiene el valor.
     * @return el valor
     */
    public String getCorreo() { return correo; }
    /**
     * Establece el valor.
     * @param valor el valor a establecer
     */
    public void setCorreo(String correo) { this.correo = correo; }
}
