package com.ecommerce.authh.infraestructure.mapper;

import com.ecommerce.authh.domain.model.Usuario;
import com.ecommerce.authh.infraestructure.driver_adapters.UsuarioData;
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
                usuarioData.getEdad()
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
                usuario.getEdad()
        );
    }
}
