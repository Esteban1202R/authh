package com.ecommerce.catalogo.domain.usecase;

import com.ecommerce.catalogo.domain.model.Producto;
import com.ecommerce.catalogo.domain.model.gateway.ProductoGateway;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ProductoUseCase {

    private final ProductoGateway productoGateway;

    public Producto guardarProducto(Producto producto) {
        validarProducto(producto);
        return productoGateway.guardarProducto(producto);
    }

    public Producto buscarPorIdProducto(Long idProducto) {
        return productoGateway.buscarPorIdProducto(idProducto);
    }

    public List<Producto> listarProductos() {
        return productoGateway.listarProductos();
    }

    public Producto actualizarProducto(Producto producto) {
        Producto productoExistente = productoGateway.buscarPorIdProducto(producto.getId());

        if (productoExistente.getId() == null) {
            throw new RuntimeException("El producto no existe");
        }

        validarProducto(producto);
        return productoGateway.actualizarProducto(producto);
    }

    public void eliminarPorIdProducto(Long idProducto) {
        Producto productoExistente = productoGateway.buscarPorIdProducto(idProducto);

        if (productoExistente.getId() == null) {
            throw new RuntimeException("El producto no existe");
        }

        productoGateway.eliminarPorIdProducto(idProducto);
    }

    // Reglas de negocio del producto
    private void validarProducto(Producto producto) {

        if (producto.getNombre() == null || producto.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto es obligatorio");
        }

        if (producto.getPrecio() == null || producto.getPrecio() <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor que cero");
        }

        if (producto.getStock() == null || producto.getStock() < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }
    }
}
