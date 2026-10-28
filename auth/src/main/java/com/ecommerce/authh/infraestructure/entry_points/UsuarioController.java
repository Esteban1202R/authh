package com.ecommerce.authh.infraestructure.entry_points;

import com.ecommerce.authh.domain.exception.NoEncontradoException;
import com.ecommerce.authh.domain.exception.ValidacionNegocioException;
import com.ecommerce.authh.domain.model.Usuario;
import com.ecommerce.authh.domain.usecase.UsuarioUseCase;
import com.ecommerce.authh.infraestructure.entry_points.dto.LoginRequest;
import com.ecommerce.authh.infraestructure.entry_points.dto.UsuarioRequest;
import com.ecommerce.authh.infraestructure.entry_points.dto.UsuarioResponse;
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
    public ResponseEntity<?> saveUsuario(@RequestBody UsuarioRequest peticion){
        try{
            Usuario usuario = mapperUsuario.toUsuario(peticion);
            Usuario usuarioValidadoGuardado = usuarioUseCase.guardarUsuario(usuario);

            if(usuarioValidadoGuardado.getId() != null){
                return new ResponseEntity<>(mapperUsuario.toResponse(usuarioValidadoGuardado), HttpStatus.OK);
            }

            return new ResponseEntity<>(mapperUsuario.toResponse(usuarioValidadoGuardado), HttpStatus.CONFLICT);
        } catch (ValidacionNegocioException reglaDeNegocio){
            return new ResponseEntity<>(reglaDeNegocio.getMessage(), HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> findByIdUsuario(@PathVariable Long id){
        Usuario usuarioValidadoEncontrado = usuarioUseCase.buscarPorIdUsuario(id);

        if(usuarioValidadoEncontrado.getId() != null){
            return new ResponseEntity<>(mapperUsuario.toResponse(usuarioValidadoEncontrado), HttpStatus.OK);
        }

        return new ResponseEntity<>(mapperUsuario.toResponse(usuarioValidadoEncontrado), HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id){
        try{
            usuarioUseCase.eliminarPorIdUsuario(id);
            return ResponseEntity.ok().body("Usuario eliminado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update")
    public ResponseEntity<UsuarioResponse> updateUsuario(@RequestBody UsuarioRequest peticion){
        try{
            Usuario usuario = mapperUsuario.toUsuario(peticion);
            Usuario usuarioValidadActualizado = usuarioUseCase.actualizaUsuario(usuario);
            return new ResponseEntity<>(mapperUsuario.toResponse(usuarioValidadActualizado), HttpStatus.OK);
        }catch (Exception error){
            return ResponseEntity.notFound().build();
        }
    }

//el requestbody convierte la informacion del Json a objeto la que se trae de postman

    @PostMapping("/login")
    public ResponseEntity<?> loginUsuario(@RequestBody LoginRequest peticion){
        try{
            String mensajeRespuesta = usuarioUseCase.loginUsuario(peticion.getCorreo(), peticion.getClave());
            return new ResponseEntity<>(mensajeRespuesta, HttpStatus.OK);
        } catch (Exception error){
            return new ResponseEntity<>("Falló el logueo", HttpStatus.OK);
        }
    }

    @GetMapping("/saldo/{id}")
    public ResponseEntity<?> consultarSaldo(@PathVariable Long id){
        try{
            Double saldo = usuarioUseCase.consultarSaldo(id);
            return new ResponseEntity<>(saldo, HttpStatus.OK);
        } catch (Exception error){
            return new ResponseEntity<>(error.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/saldo/recargar/{id}/{monto}")
    public ResponseEntity<?> recargarSaldo(@PathVariable Long id, @PathVariable Double monto){
        try{
            Usuario usuario = usuarioUseCase.recargarSaldo(id, monto);
            return new ResponseEntity<>(mapperUsuario.toResponse(usuario), HttpStatus.OK);
        } catch (ValidacionNegocioException reglaDeNegocio){
            return new ResponseEntity<>(reglaDeNegocio.getMessage(), HttpStatus.OK);
        } catch (NoEncontradoException noExiste){
            return new ResponseEntity<>(noExiste.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/saldo/descontar/{id}/{monto}")
    public ResponseEntity<?> descontarSaldo(@PathVariable Long id, @PathVariable Double monto){
        try{
            Usuario usuario = usuarioUseCase.descontarSaldo(id, monto);
            return new ResponseEntity<>(mapperUsuario.toResponse(usuario), HttpStatus.OK);
        } catch (ValidacionNegocioException reglaDeNegocio){
            return new ResponseEntity<>(reglaDeNegocio.getMessage(), HttpStatus.OK);
        } catch (NoEncontradoException noExiste){
            return new ResponseEntity<>(noExiste.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
