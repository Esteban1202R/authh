package com.ecommerce.catalogo.domain.model.gateway;

import com.ecommerce.catalogo.domain.model.Usuario;

// lo que necesito de auth
public interface UsuarioGateway {

    Usuario buscarPorIdUsuario(Long idUsuario);
    void descontarSaldo(Long idUsuario, Double monto);
}
