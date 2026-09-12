package com.ecommerce.authh.infraestructure.entry_points;

import com.ecommerce.authh.domain.model.Usuario;
import com.ecommerce.authh.domain.usecase.UsuarioUseCase;
import com.ecommerce.authh.infraestructure.driver_adapters.UsuarioData;
import com.ecommerce.authh.infraestructure.mapper.MapperUsuario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioUseCase usuarioUseCase;
    private final MapperUsuario mapperUsuario;

    public UsuarioController(UsuarioUseCase usuarioUseCase, MapperUsuario mapperUsuario) {
        this.usuarioUseCase = usuarioUseCase;
        this.mapperUsuario = mapperUsuario;
    }

    @PostMapping("/save")
    public ResponseEntity<Usuario> saveUsuario(@RequestBody UsuarioData usuarioData) {
        Usuario usuario = mapperUsuario.toUsuario(usuarioData);
        Usuario usuarioValidadoGuardado = usuarioUseCase.guardarUsuario(usuario);

        if (usuarioValidadoGuardado.getId() != null) {
            return new ResponseEntity<>(usuarioValidadoGuardado, HttpStatus.OK);
        }

        return new ResponseEntity<>(usuarioValidadoGuardado, HttpStatus.CONFLICT);
    }
}