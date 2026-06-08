package org.example.modules.clientes.dto;

import org.example.modules.clientes.entity.Cliente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Objeto de Transferencia de Datos (DTO) para Cliente.
 */
public class ClienteDTO {

    private Long id;

    @NotBlank(message = "El campo nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El campo apellido es obligatorio")
    private String apellido;

    @NotBlank(message = "El campo telefono es obligatorio")
    private String telefono;

    @NotBlank(message = "El campo correo es obligatorio")
    private String correo;

    @NotBlank(message = "El campo direccion es obligatorio")

    private String direccion;

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
    public String getApellido() { return apellido; }
    /**
     * Establece el valor.
     * @param valor el valor a establecer
     */
    public void setApellido(String apellido) { this.apellido = apellido; }
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
    /**
     * Obtiene el valor.
     * @return el valor
     */
    public String getDireccion() { return direccion; }
    /**
     * Establece el valor.
     * @param valor el valor a establecer
     */
    public void setDireccion(String direccion) { this.direccion = direccion; }
}

