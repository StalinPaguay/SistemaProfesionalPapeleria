


package org.example.exception;

// Excepción personalizada para recursos no encontrados (HTTP 404)
public class RecursoNoEncontradoException extends RuntimeException {
    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}

