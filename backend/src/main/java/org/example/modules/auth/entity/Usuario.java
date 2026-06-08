package org.example.modules.auth.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "usuarios")
/**
 * Entidad que representa a Usuario.
 */
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El campo nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El campo apellido es obligatorio")
    private String apellido;

    @NotBlank(message = "El campo correo es obligatorio")
    private String correo;

    @NotBlank(message = "El campo password es obligatorio")
    private String password;

    @NotNull(message = "El rol es obligatorio")
    @Enumerated(EnumType.STRING)
    private Rol rol;

    public Usuario() {
    }

    /**

     * Obtiene el valor.

     * @return el valor

     */

    public Long getId() {
        return id;
    }

    /**

     * Establece el valor.

     * @param valor el valor a establecer

     */

    public void setId(Long id) {
        this.id = id;
    }

    /**

     * Obtiene el valor.

     * @return el valor

     */

    public String getNombre() {
        return nombre;
    }

    /**

     * Establece el valor.

     * @param valor el valor a establecer

     */

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**

     * Obtiene el valor.

     * @return el valor

     */

    public String getApellido() {
        return apellido;
    }

    /**

     * Establece el valor.

     * @param valor el valor a establecer

     */

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**

     * Obtiene el valor.

     * @return el valor

     */

    public String getCorreo() {
        return correo;
    }

    /**

     * Establece el valor.

     * @param valor el valor a establecer

     */

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**

     * Obtiene el valor.

     * @return el valor

     */

    public String getPassword() {
        return password;
    }

    /**

     * Establece el valor.

     * @param valor el valor a establecer

     */

    public void setPassword(String password) {
        this.password = password;
    }

    /**

     * Obtiene el valor.

     * @return el valor

     */

    public Rol getRol() {
        return rol;
    }

    /**

     * Establece el valor.

     * @param valor el valor a establecer

     */

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}

