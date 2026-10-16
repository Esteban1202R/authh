package com.ecommerce.authh.domain.exception;

// El sistema funciono bien: detecto datos o una condicion que la regla de
// negocio no permite. La peticion se atiende y se informa el motivo.
public class ValidacionNegocioException extends RuntimeException {
    public ValidacionNegocioException(String message) {
        super(message);
    }
}
