package com.ecommerce.authh.domain.model.gateway;

import com.ecommerce.authh.domain.model.Usuario;

public interface UsuarioGateway {

    Usuario guardarUsuario(Usuario Usuario);
    Usuario buscarPorIdUsuario(String idUsuario);
    Usuario ActualizarUsuario (Usuario Usuario);
    void eliminarPorUsuario(String idUsuario);

    Usuario guardar(Usuario usuario);
}
