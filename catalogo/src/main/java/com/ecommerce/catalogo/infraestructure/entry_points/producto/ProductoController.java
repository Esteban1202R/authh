package com.ecommerce.catalogo.infraestructure.entry_points.producto;

import com.ecommerce.catalogo.domain.exception.NoEncontradoException;
import com.ecommerce.catalogo.domain.exception.ValidacionNegocioException;
import com.ecommerce.catalogo.domain.model.Producto;
import com.ecommerce.catalogo.domain.usecase.ProductoUseCase;
import com.ecommerce.catalogo.infraestructure.driver_adapters.jpa_repository.ProductoData;
import com.ecommerce.catalogo.infraestructure.mapper.MapperProducto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ecommerce/producto")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoUseCase productoUseCase;
    private final MapperProducto mapperProducto;

    @PostMapping("/save")
    public ResponseEntity<?> saveProducto(@RequestBody ProductoData productoData){
        try{
            Producto producto = mapperProducto.toProducto(productoData);
            Producto productoGuardado = productoUseCase.guardarProducto(producto);
            return new ResponseEntity<>(productoGuardado, HttpStatus.OK);
        } catch (ValidacionNegocioException reglaDeNegocio){
            // El sistema valido bien: se informa el motivo con 200
            return new ResponseEntity<>(reglaDeNegocio.getMessage(), HttpStatus.OK);
        } catch (NoEncontradoException noExiste){
            return new ResponseEntity<>(noExiste.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Producto>> listarProductos(){
        return new ResponseEntity<>(productoUseCase.listarProductos(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> findByIdProducto(@PathVariable Long id){
        Producto producto = productoUseCase.buscarPorIdProducto(id);

        if(producto.getId() != null){
            return new ResponseEntity<>(producto, HttpStatus.OK);
        }

        return new ResponseEntity<>(producto, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateProducto(@RequestBody ProductoData productoData){
        try{
            Producto producto = mapperProducto.toProducto(productoData);
            Producto productoActualizado = productoUseCase.actualizarProducto(producto);
            return new ResponseEntity<>(productoActualizado, HttpStatus.OK);
        } catch (ValidacionNegocioException reglaDeNegocio){
            // El sistema valido bien: se informa el motivo con 200
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
