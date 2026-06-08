package org.example.modules.clientes.entity;

import org.example.modules.ventas.entity.Venta;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clientes")
/**
 * Entidad que representa a Cliente.
 */
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El campo nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El campo apellido es obligatorio")
    private String apellido;

    @NotBlank(message = "El campo correo es obligatorio")
    private String correo;

    @NotBlank(message = "El campo direccion es obligatorio")

    private String direccion;

    @NotBlank(message = "El campo telefono es obligatorio")
    private String telefono;
    // Relación uno a muchos con ventas (se añadirá después de crear la entidad Venta)
    // @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // private List<Venta> ventas = new ArrayList<>();

    public Cliente() {}

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

