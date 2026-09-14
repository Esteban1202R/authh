package com.ecommerce.authh.infraestructure.entry_points;

import com.ecommerce.authh.domain.model.Usuario;
import com.ecommerce.authh.domain.usecase.UsuarioUseCase;
import com.ecommerce.authh.infraestructure.driver_adapters.UsuarioData;
import com.ecommerce.authh.infraestructure.mapper.MapperUsuario;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ecommerce/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioUseCase usuarioUseCase;
    private final MapperUsuario mapperUsuario;

    @PostMapping("/save")
    public ResponseEntity<Usuario> saveUsuario(@RequestBody UsuarioData usuarioData){
        Usuario usuario = mapperUsuario.toUsuario(usuarioData);
        Usuario usuarioValidadoGuardado = usuarioUseCase.guardarUsuario(usuario);

        if(usuarioValidadoGuardado.getId() != null){
            return new ResponseEntity<>(usuarioValidadoGuardado, HttpStatus.OK);
        }

        return new ResponseEntity<>(usuarioValidadoGuardado, HttpStatus.CONFLICT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> findByIdUsuario(@PathVariable String id){
        Usuario usuarioValidadoEncontrado = usuarioUseCase.buscarPorIdUsuario(id);

        if(usuarioValidadoEncontrado.getId() != null){
            return new ResponseEntity<>(usuarioValidadoEncontrado, HttpStatus.OK);
        }

        return new ResponseEntity<>(usuarioValidadoEncontrado, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable String id){
        try{
            usuarioUseCase.eliminarPorIdUsuario(id);
            return ResponseEntity.ok().body("Usuario eliminado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Usuario> updateUsuario(@RequestBody UsuarioData usuarioData){
        try{
            Usuario usuario = mapperUsuario.toUsuario(usuarioData);
            Usuario usuarioValidadActualizado = usuarioUseCase.actualizaUsuario(usuario);
            return new ResponseEntity<>(usuarioValidadActualizado, HttpStatus.OK);
        }catch (Exception error){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUsuario(@RequestBody UsuarioData usuarioData){
        try{
            String mensajeRespuesta = usuarioUseCase.loginUsuario(usuarioData.getCorreo(),usuarioData.getClave());
            return new ResponseEntity<>(mensajeRespuesta, HttpStatus.OK);
        } catch (Exception error){
            return new ResponseEntity<>("Falló el logueo", HttpStatus.CONFLICT);
        }
    }

}
