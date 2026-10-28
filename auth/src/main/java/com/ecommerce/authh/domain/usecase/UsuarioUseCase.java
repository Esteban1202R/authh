package com.ecommerce.authh.domain.usecase;

import com.ecommerce.authh.domain.exception.NoEncontradoException;
import com.ecommerce.authh.domain.exception.ValidacionNegocioException;
import com.ecommerce.authh.domain.model.Usuario;
import com.ecommerce.authh.domain.model.gateway.EncrypterGateway;
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
    private final EncrypterGateway encrypterGateway;

    public Usuario guardarUsuario(Usuario usuario) {

        if (usuario.getCorreo() == null || usuario.getClave() == null){

            throw new ValidacionNegocioException("Hay campos vacios");
        }

        if(usuario.getEdad() < 18){
            throw new ValidacionNegocioException("Menor de edad");
        }


        validarFormatoCorreo(usuario.getCorreo());
        validarClave(usuario.getClave());
        validarNombre(usuario.getNombre());

        String claveEncriptada = encrypterGateway.encrypt(usuario.getClave());
        usuario.setClave(claveEncriptada);

        // saldo en 0
        usuario.setSaldo(0.0);

        Usuario usuarioGuardado = usuarioGateway.guardarUsuario(usuario);
        return usuarioGuardado;

    }

    public Usuario buscarPorIdUsuario(Long idUsuario) {
        return usuarioGateway.buscarPorIdUsuario(idUsuario);
    }

    public void eliminarPorIdUsuario(Long idUsuario) {
        Usuario usuarioExistente = usuarioGateway.buscarPorIdUsuario(idUsuario);

        if (usuarioExistente.getId() == null) {
            throw new NoEncontradoException("El usuario no existe");
        }

        usuarioGateway.eliminarPorUsuario(idUsuario);
    }

    public Usuario actualizaUsuario(Usuario usuario) {
        Usuario usuarioExistente = usuarioGateway.buscarPorIdUsuario(usuario.getId());

        if (usuarioExistente.getId() == null) {
            throw new NoEncontradoException("El usuario no existe");
        }

        String claveEncriptada = encrypterGateway.encrypt(usuario.getClave());
        usuario.setClave(claveEncriptada);

        usuario.setSaldo(usuarioExistente.getSaldo());

        return usuarioGateway.ActualizarUsuario(usuario);
    }

    public Double consultarSaldo(Long idUsuario) {
        Usuario usuario = usuarioGateway.buscarPorIdUsuario(idUsuario);

        if (usuario.getId() == null) {
            throw new NoEncontradoException("El usuario no existe");
        }

        return usuario.getSaldo();
    }

    public Usuario recargarSaldo(Long idUsuario, Double monto) {
        Usuario usuario = buscarUsuarioParaSaldo(idUsuario, monto);

        usuario.setSaldo(usuario.getSaldo() + monto);

        return usuarioGateway.ActualizarUsuario(usuario);
    }

    public Usuario descontarSaldo(Long idUsuario, Double monto) {
        Usuario usuario = buscarUsuarioParaSaldo(idUsuario, monto);

        if (usuario.getSaldo() < monto) {
            throw new ValidacionNegocioException("Saldo insuficiente");
        }

        usuario.setSaldo(usuario.getSaldo() - monto);

        return usuarioGateway.ActualizarUsuario(usuario);
    }


    private Usuario buscarUsuarioParaSaldo(Long idUsuario, Double monto) {
        Usuario usuario = usuarioGateway.buscarPorIdUsuario(idUsuario);

        if (usuario.getId() == null) {
            throw new NoEncontradoException("El usuario no existe");
        }

        if (monto == null || monto <= 0) {
            throw new ValidacionNegocioException("El monto debe ser mayor que cero");
        }

        return usuario;
    }

    public String loginUsuario(String correo, String clave) {
        Usuario usuario = usuarioGateway.buscarPorCorreo(correo);

        if (usuario.getId() == null || !encrypterGateway.checkPass(clave, usuario.getClave())) {
            throw new ValidacionNegocioException("Credenciales incorrectas");
        }

        return "Usuario logueado exitosamente";
    }

    private void validarFormatoCorreo(String correo) {
        Optional.of(correo)
                .map(String::trim)
                .filter(c -> !c.isEmpty())
                .filter(c -> c.matches("^[\\w.+-]+@[\\w-]+\\.[\\w.]+$"))
                .orElseThrow(() -> new ValidacionNegocioException("El correo no tiene un formato valido"));
    }

    // Clave con longitud minima de 8 caracteres
    private void validarClave(String clave) {
        Optional.of(clave)
                .filter(c -> c.length() >= 8)
                .orElseThrow(() -> new ValidacionNegocioException("La clave debe tener al menos 8 caracteres"));
    }

    // Nombre obligatorio y no vacio
    private void validarNombre(String nombre) {
        Optional.ofNullable(nombre)
                .map(String::trim)
                .filter(n -> !n.isEmpty())
                .orElseThrow(() -> new ValidacionNegocioException("El nombre es obligatorio"));
    }
}