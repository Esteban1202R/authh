package com.ecommerce.authh.domain.exception;

// falla una regla de negocio, responde 200 con el mensaje
public class ValidacionNegocioException extends RuntimeException {
    public ValidacionNegocioException(String message) {
        super(message);
    }
}
