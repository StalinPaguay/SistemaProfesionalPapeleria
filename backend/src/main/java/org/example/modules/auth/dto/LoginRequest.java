package org.example.modules.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Entidad que representa a LoginRequest.
 */
public class LoginRequest {

    @NotBlank(message = "El campo correo es obligatorio")
    private String correo;

    @NotBlank(message = "El campo password es obligatorio")
    private String password;

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
    public String getPassword() { return password; }
    /**
     * Establece el valor.
     * @param valor el valor a establecer
     */
    public void setPassword(String password) { this.password = password; }
}

