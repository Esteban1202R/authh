package com.ecommerce.authh.domain.usecase;

import com.ecommerce.authh.domain.model.Usuario;
import com.ecommerce.authh.domain.model.gateway.UsuarioGateway;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor

//variaciones mas robustas
//validar el resto sin usar estructuras de control condicional no usar librerias externas
//estoy implementando validaciones de datos sin usar estructuras de control ni librerias porque
// valdidaciones de negocios

public class UsuarioUseCase {

    private final UsuarioGateway usuarioGateway;

    public Usuario guardarUsuario(Usuario usuario) {

        if (usuario.getCorreo() == null || usuario.getClave() == null){

            throw new NullPointerException("Hay campos vacios");
        }

        if(usuario.getEdad() < 18){
            throw new RuntimeException("Menor de edad");
        }


        validarFormatoCorreo(usuario.getCorreo());
        validarClave(usuario.getClave());
        validarNombre(usuario.getNombre());

        Usuario usuarioGuardado = usuarioGateway.guardarUsuario(usuario);
        return usuarioGuardado;

    }

    public Usuario buscarPorIdUsuario(Long idUsuario) {
        return usuarioGateway.buscarPorIdUsuario(idUsuario);
    }

    public void eliminarPorIdUsuario(Long idUsuario) {
        Usuario usuarioExistente = usuarioGateway.buscarPorIdUsuario(idUsuario);

        if (usuarioExistente.getId() == null) {
            throw new RuntimeException("El usuario no existe");
        }

        usuarioGateway.eliminarPorUsuario(idUsuario);
    }

    public Usuario actualizaUsuario(Usuario usuario) {
        Usuario usuarioExistente = usuarioGateway.buscarPorIdUsuario(usuario.getId());

        if (usuarioExistente.getId() == null) {
            throw new RuntimeException("El usuario no existe");
        }

        return usuarioGateway.ActualizarUsuario(usuario);
    }

    public String loginUsuario(String correo, String clave) {
        Usuario usuario = usuarioGateway.buscarPorCorreo(correo);

        if (usuario.getId() == null || !usuario.getClave().equals(clave)) {
            throw new RuntimeException("Credenciales incorrectas");
        }

        return "Usuario logueado exitosamente";
    }

    private void validarFormatoCorreo(String correo) {
        Optional.of(correo)
                .map(String::trim)
                .filter(c -> !c.isEmpty())
                .filter(c -> c.matches("^[\\w.+-]+@[\\w-]+\\.[\\w.]+$"))
                .orElseThrow(() -> new IllegalArgumentException("El correo no tiene un formato valido"));
    }

    // Clave con longitud minima de 8 caracteres
    private void validarClave(String clave) {
        Optional.of(clave)
                .filter(c -> c.length() >= 8)
                .orElseThrow(() -> new IllegalArgumentException("La clave debe tener al menos 8 caracteres"));
    }

    // Nombre obligatorio y no vacio
    private void validarNombre(String nombre) {
        Optional.ofNullable(nombre)
                .map(String::trim)
                .filter(n -> !n.isEmpty())
                .orElseThrow(() -> new IllegalArgumentException("El nombre es obligatorio"));
    }
}