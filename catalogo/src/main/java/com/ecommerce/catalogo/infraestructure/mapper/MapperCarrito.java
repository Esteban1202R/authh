package com.ecommerce.catalogo.infraestructure.mapper;

import com.ecommerce.catalogo.domain.model.Carrito;
import com.ecommerce.catalogo.domain.model.ItemCarrito;
import com.ecommerce.catalogo.infraestructure.driver_adapters.jpa_repository.CarritoData;
import com.ecommerce.catalogo.infraestructure.driver_adapters.jpa_repository.ItemCarritoData;
import com.ecommerce.catalogo.infraestructure.entry_points.carrito.dto.CarritoResponse;
import com.ecommerce.catalogo.infraestructure.entry_points.carrito.dto.ItemCarritoResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MapperCarrito {

    public Carrito toCarrito(CarritoData carritoData) {
        List<ItemCarrito> items = new ArrayList<>();

        for (ItemCarritoData itemData : carritoData.getItems()) {
            items.add(new ItemCarrito(
                    itemData.getId(),
                    itemData.getIdProducto(),
                    itemData.getNombreProducto(),
                    itemData.getCantidad(),
                    itemData.getPrecioUnitario(),
                    itemData.getSubtotal()
            ));
        }

        return new Carrito(
                carritoData.getId(),
                carritoData.getIdUsuario(),
                carritoData.getEstado(),
                carritoData.getTotal(),
                items
        );
    }

    public CarritoData toData(Carrito carrito) {
        CarritoData carritoData = new CarritoData();
        carritoData.setId(carrito.getId());
        carritoData.setIdUsuario(carrito.getIdUsuario());
        carritoData.setEstado(carrito.getEstado());
        carritoData.setTotal(carrito.getTotal());

        List<ItemCarritoData> items = new ArrayList<>();

        for (ItemCarrito item : carrito.getItems()) {
            ItemCarritoData itemData = new ItemCarritoData();
            itemData.setId(item.getId());
            itemData.setCarrito(carritoData);   // relacion en los dos sentidos
            itemData.setIdProducto(item.getIdProducto());
            itemData.setNombreProducto(item.getNombreProducto());
            itemData.setCantidad(item.getCantidad());
            itemData.setPrecioUnitario(item.getPrecioUnitario());
            itemData.setSubtotal(item.getSubtotal());
            items.add(itemData);
        }

        carritoData.setItems(items);
        return carritoData;
    }

    // del dominio al json
    public CarritoResponse toResponse(Carrito carrito) {
        List<ItemCarritoResponse> items = new ArrayList<>();

        for (ItemCarrito item : carrito.getItems()) {
            items.add(new ItemCarritoResponse(
                    item.getId(),
                    item.getIdProducto(),
                    item.getNombreProducto(),
                    item.getCantidad(),
                    item.getPrecioUnitario(),
                    item.getSubtotal()
            ));
        }

        return new CarritoResponse(
                carrito.getId(),
                carrito.getIdUsuario(),
                carrito.getEstado(),
                carrito.getTotal(),
                items
        );
    }
}
