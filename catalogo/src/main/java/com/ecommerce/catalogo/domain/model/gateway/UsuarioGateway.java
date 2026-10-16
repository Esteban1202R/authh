package com.ecommerce.catalogo.domain.model.gateway;

import com.ecommerce.catalogo.domain.model.Usuario;

// Puerto hacia el microservicio de autenticacion. El dominio no sabe que por
// detras hay una llamada REST: solo pide los datos del usuario y el descuento.
public interface UsuarioGateway {

    Usuario buscarPorIdUsuario(Long idUsuario);
    void descontarSaldo(Long idUsuario, Double monto);
}
