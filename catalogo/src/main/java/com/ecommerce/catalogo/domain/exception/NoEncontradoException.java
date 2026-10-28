package com.ecommerce.catalogo.domain.exception;

// no existe el recurso, va con 404
public class NoEncontradoException extends RuntimeException {
    public NoEncontradoException(String message) {
        super(message);
    }
}
