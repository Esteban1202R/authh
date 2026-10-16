package com.ecommerce.catalogo.domain.model.gateway;

import com.ecommerce.catalogo.domain.model.Carrito;

public interface CarritoGateway {

    Carrito guardarCarrito(Carrito carrito);
    Carrito buscarCarritoActivo(Long idUsuario);
    Carrito buscarPorIdCarrito(Long idCarrito);
}
