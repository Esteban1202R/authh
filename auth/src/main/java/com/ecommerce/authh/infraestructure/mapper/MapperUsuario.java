package com.ecommerce.authh.infraestructure.mapper;

import com.ecommerce.authh.domain.model.Usuario;
import com.ecommerce.authh.infraestructure.driver_adapters.UsuarioData;
import com.ecommerce.authh.infraestructure.entry_points.dto.UsuarioRequest;
import com.ecommerce.authh.infraestructure.entry_points.dto.UsuarioResponse;
import org.springframework.stereotype.Component;

@Component
public class MapperUsuario {

    public Usuario toUsuario(UsuarioData usuarioData) {
        return new Usuario(
                usuarioData.getId(),
                usuarioData.getNombre(),
                usuarioData.getCorreo(),
                usuarioData.getClave(),
                usuarioData.getRol(),
                usuarioData.getNumerodecelular(),
                usuarioData.getEdad(),
                usuarioData.getSaldo()
        );
    }

    public UsuarioData toData(Usuario usuario) {
        return new UsuarioData(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getCorreo(),
                usuario.getClave(),
                usuario.getRol(),
                usuario.getNumerodecelular(),
                usuario.getEdad(),
                usuario.getSaldo()
        );
    }

    // del json al dominio. el saldo no viene del cliente
    public Usuario toUsuario(UsuarioRequest peticion) {
        return new Usuario(
                peticion.getId(),
                peticion.getNombre(),
                peticion.getCorreo(),
                peticion.getClave(),
                peticion.getRol(),
                peticion.getNumerodecelular(),
                peticion.getEdad(),
                null
        );
    }

    // del dominio al json. aca no va la clave
    public UsuarioResponse toResponse(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getCorreo(),
                usuario.getRol(),
                usuario.getNumerodecelular(),
                usuario.getEdad(),
                usuario.getSaldo()
        );
    }
}
