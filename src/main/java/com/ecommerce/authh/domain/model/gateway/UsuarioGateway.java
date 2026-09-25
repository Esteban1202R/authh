package com.ecommerce.authh.domain.model.gateway;

import com.ecommerce.authh.domain.model.Usuario;

public interface UsuarioGateway {

    Usuario guardarUsuario(Usuario Usuario);
    Usuario buscarPorIdUsuario(Long idUsuario);
    Usuario ActualizarUsuario (Usuario Usuario);
    void eliminarPorUsuario(Long idUsuario);

    Usuario guardar(Usuario usuario);
}
