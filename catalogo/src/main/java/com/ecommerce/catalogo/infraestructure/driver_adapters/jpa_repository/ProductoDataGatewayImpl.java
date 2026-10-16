package com.ecommerce.catalogo.infraestructure.driver_adapters.jpa_repository;

import com.ecommerce.catalogo.domain.model.Producto;
import com.ecommerce.catalogo.domain.model.gateway.ProductoGateway;
import com.ecommerce.catalogo.infraestructure.mapper.MapperProducto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductoDataGatewayImpl implements ProductoGateway {

    private final MapperProducto mapperProducto;
    private final ProductoDataJpaRepository repository;

    @Override
    public Producto guardarProducto(Producto producto) {
        ProductoData productoData = mapperProducto.toData(producto);
        return mapperProducto.toProducto(repository.save(productoData));
    }

    @Override
    public Producto buscarPorIdProducto(Long idProducto) {
        return repository.findById(idProducto)
                .map(mapperProducto::toProducto)
                .orElse(new Producto());
    }

    @Override
    public List<Producto> listarProductos() {
        List<Producto> productos = new ArrayList<>();

        for (ProductoData productoData : repository.findAll()) {
            productos.add(mapperProducto.toProducto(productoData));
        }

        return productos;
    }

    @Override
    public Producto actualizarProducto(Producto producto) {
        return guardarProducto(producto);
    }

    @Override
    public void eliminarPorIdProducto(Long idProducto) {
        repository.deleteById(idProducto);
    }
}
