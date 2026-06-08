package org.example.exception;

// Excepción personalizada para reglas de negocio violadas (HTTP 400)
/**
 * Entidad que representa a ReglaNegocioException.
 */
public class ReglaNegocioException extends RuntimeException {
    public ReglaNegocioException(String mensaje) {
        super(mensaje);
    }
}

