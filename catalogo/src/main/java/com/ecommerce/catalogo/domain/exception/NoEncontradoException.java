package com.ecommerce.catalogo.domain.exception;

// El recurso que se pidio no existe. Eso si es un error de la peticion.
public class NoEncontradoException extends RuntimeException {
    public NoEncontradoException(String message) {
        super(message);
    }
}
