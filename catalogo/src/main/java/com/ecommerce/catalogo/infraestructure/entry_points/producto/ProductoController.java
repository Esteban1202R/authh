package com.ecommerce.catalogo.infraestructure.entry_points.producto;

import com.ecommerce.catalogo.domain.exception.NoEncontradoException;
import com.ecommerce.catalogo.domain.exception.ValidacionNegocioException;
import com.ecommerce.catalogo.domain.model.Producto;
import com.ecommerce.catalogo.domain.usecase.ProductoUseCase;
import com.ecommerce.catalogo.infraestructure.entry_points.producto.dto.ProductoRequest;
import com.ecommerce.catalogo.infraestructure.entry_points.producto.dto.ProductoResponse;
import com.ecommerce.catalogo.infraestructure.mapper.MapperProducto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/ecommerce/producto")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoUseCase productoUseCase;
    private final MapperProducto mapperProducto;

    @PostMapping("/save")
    public ResponseEntity<?> saveProducto(@RequestBody ProductoRequest peticion){
        try{
            Producto producto = mapperProducto.toProducto(peticion);
            Producto productoGuardado = productoUseCase.guardarProducto(producto);
            return new ResponseEntity<>(mapperProducto.toResponse(productoGuardado), HttpStatus.OK);
        } catch (ValidacionNegocioException reglaDeNegocio){
            return new ResponseEntity<>(reglaDeNegocio.getMessage(), HttpStatus.OK);
        } catch (NoEncontradoException noExiste){
            return new ResponseEntity<>(noExiste.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductoResponse>> listarProductos(){
        List<ProductoResponse> respuesta = new ArrayList<>();

        for (Producto producto : productoUseCase.listarProductos()) {
            respuesta.add(mapperProducto.toResponse(producto));
        }

        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponse> findByIdProducto(@PathVariable Long id){
        Producto producto = productoUseCase.buscarPorIdProducto(id);

        if(producto.getId() != null){
            return new ResponseEntity<>(mapperProducto.toResponse(producto), HttpStatus.OK);
        }

        return new ResponseEntity<>(mapperProducto.toResponse(producto), HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateProducto(@RequestBody ProductoRequest peticion){
        try{
            Producto producto = mapperProducto.toProducto(peticion);
            Producto productoActualizado = productoUseCase.actualizarProducto(producto);
            return new ResponseEntity<>(mapperProducto.toResponse(productoActualizado), HttpStatus.OK);
        } catch (ValidacionNegocioException reglaDeNegocio){
            return new ResponseEntity<>(reglaDeNegocio.getMessage(), HttpStatus.OK);
        } catch (NoEncontradoException noExiste){
            return new ResponseEntity<>(noExiste.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id){
        try{
            productoUseCase.eliminarPorIdProducto(id);
            return ResponseEntity.ok().body("Producto eliminado exitosamente");
        } catch (Exception error){
            return ResponseEntity.notFound().build();
        }
    }
}
