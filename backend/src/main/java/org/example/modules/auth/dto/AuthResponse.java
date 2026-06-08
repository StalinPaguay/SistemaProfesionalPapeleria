package org.example.modules.auth.dto;

import jakarta.validation.constraints.*;

/**
 * Entidad que representa a AuthResponse.
 */
public class AuthResponse {
    private String token;
    private String rol;
    
    public AuthResponse() {}

    @NotBlank(message = "El campo correo es obligatorio")
    private String correo;
    @NotBlank(message = "El campo nombreCompleto es obligatorio")
    private String nombreCompleto;

    public AuthResponse(String token, String correo, String rol, String nombreCompleto) {
        this.token = token;
        this.correo = correo;
        this.rol = rol;
        this.nombreCompleto = nombreCompleto;
    }

    /**

     * Obtiene el valor.

     * @return el valor

     */

    public String getToken() { return token; }
    /**
     * Establece el valor.
     * @param valor el valor a establecer
     */
    public void setToken(String token) { this.token = token; }
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
    public String getRol() { return rol; }
    /**
     * Establece el valor.
     * @param valor el valor a establecer
     */
    public void setRol(String rol) { this.rol = rol; }
    /**
     * Obtiene el valor.
     * @return el valor
     */
    public String getNombreCompleto() { return nombreCompleto; }
    /**
     * Establece el valor.
     * @param valor el valor a establecer
     */
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
}

