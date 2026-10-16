package com.ecommerce.catalogo.infraestructure.entry_points.carrito;

import com.ecommerce.catalogo.domain.model.Carrito;
import com.ecommerce.catalogo.domain.usecase.CarritoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ecommerce/carrito")
@RequiredArgsConstructor
public class CarritoController {

    private final CarritoUseCase carritoUseCase;

    @PostMapping("/crear/{idUsuario}")
    public ResponseEntity<?> crearCarrito(@PathVariable Long idUsuario){
        try{
            Carrito carrito = carritoUseCase.crearCarrito(idUsuario);
            return new ResponseEntity<>(carrito, HttpStatus.OK);
        } catch (Exception error){
            return new ResponseEntity<>(error.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/{idUsuario}")
    public ResponseEntity<?> consultarCarrito(@PathVariable Long idUsuario){
        try{
            Carrito carrito = carritoUseCase.consultarCarrito(idUsuario);
            return new ResponseEntity<>(carrito, HttpStatus.OK);
        } catch (Exception error){
            return new ResponseEntity<>(error.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/agregar/{idUsuario}/{idProducto}/{cantidad}")
    public ResponseEntity<?> agregarProducto(@PathVariable Long idUsuario,
                                             @PathVariable Long idProducto,
                                             @PathVariable Integer cantidad){
        try{
            Carrito carrito = carritoUseCase.agregarProducto(idUsuario, idProducto, cantidad);
            return new ResponseEntity<>(carrito, HttpStatus.OK);
        } catch (Exception error){
            return new ResponseEntity<>(error.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/actualizar/{idUsuario}/{idProducto}/{cantidad}")
    public ResponseEntity<?> actualizarCantidad(@PathVariable Long idUsuario,
                                                @PathVariable Long idProducto,
                                                @PathVariable Integer cantidad){
        try{
            Carrito carrito = carritoUseCase.actualizarCantidad(idUsuario, idProducto, cantidad);
            return new ResponseEntity<>(carrito, HttpStatus.OK);
        } catch (Exception error){
            return new ResponseEntity<>(error.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/quitar/{idUsuario}/{idProducto}")
    public ResponseEntity<?> quitarProducto(@PathVariable Long idUsuario, @PathVariable Long idProducto){
        try{
            Carrito carrito = carritoUseCase.quitarProducto(idUsuario, idProducto);
            return new ResponseEntity<>(carrito, HttpStatus.OK);
        } catch (Exception error){
            return new ResponseEntity<>(error.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/comprar/{idUsuario}")
    public ResponseEntity<?> comprar(@PathVariable Long idUsuario){
        try{
            Carrito carrito = carritoUseCase.comprar(idUsuario);
            return new ResponseEntity<>(carrito, HttpStatus.OK);
        } catch (Exception error){
            return new ResponseEntity<>(error.getMessage(), HttpStatus.CONFLICT);
        }
    }
}
