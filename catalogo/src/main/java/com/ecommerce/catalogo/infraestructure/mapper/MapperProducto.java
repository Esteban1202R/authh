package com.ecommerce.catalogo.infraestructure.mapper;

import com.ecommerce.catalogo.domain.model.Producto;
import com.ecommerce.catalogo.infraestructure.driver_adapters.jpa_repository.ProductoData;
import com.ecommerce.catalogo.infraestructure.entry_points.producto.dto.ProductoRequest;
import com.ecommerce.catalogo.infraestructure.entry_points.producto.dto.ProductoResponse;
import org.springframework.stereotype.Component;

@Component
public class MapperProducto {

    public Producto toProducto(ProductoData productoData) {
        return new Producto(
                productoData.getId(),
                productoData.getNombre(),
                productoData.getDescripcion(),
                productoData.getPrecio(),
                productoData.getStock(),
                productoData.getImagenUrl()
        );
    }

    public ProductoData toData(Producto producto) {
        return new ProductoData(
                producto.getId(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecio(),
                producto.getStock(),
                producto.getImagenUrl()
        );
    }

    // del json al dominio
    public Producto toProducto(ProductoRequest peticion) {
        return new Producto(
                peticion.getId(),
                peticion.getNombre(),
                peticion.getDescripcion(),
                peticion.getPrecio(),
                peticion.getStock(),
                peticion.getImagenUrl()
        );
    }

    // del dominio al json
    public ProductoResponse toResponse(Producto producto) {
        return new ProductoResponse(
                producto.getId(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecio(),
                producto.getStock(),
                producto.getImagenUrl()
        );
    }
}
