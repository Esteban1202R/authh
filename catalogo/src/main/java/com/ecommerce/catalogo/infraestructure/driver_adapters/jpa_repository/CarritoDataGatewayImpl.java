package com.ecommerce.catalogo.infraestructure.driver_adapters.jpa_repository;

import com.ecommerce.catalogo.domain.model.Carrito;
import com.ecommerce.catalogo.domain.model.gateway.CarritoGateway;
import com.ecommerce.catalogo.infraestructure.mapper.MapperCarrito;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CarritoDataGatewayImpl implements CarritoGateway {

    private final MapperCarrito mapperCarrito;
    private final CarritoDataJpaRepository repository;

    @Override
    public Carrito guardarCarrito(Carrito carrito) {
        CarritoData carritoData = mapperCarrito.toData(carrito);
        return mapperCarrito.toCarrito(repository.save(carritoData));
    }

    @Override
    public Carrito buscarCarritoActivo(Long idUsuario) {
        return repository.findFirstByIdUsuarioAndEstado(idUsuario, "ACTIVO")
                .map(mapperCarrito::toCarrito)
                .orElse(new Carrito());
    }

    @Override
    public Carrito buscarPorIdCarrito(Long idCarrito) {
        return repository.findById(idCarrito)
                .map(mapperCarrito::toCarrito)
                .orElse(new Carrito());
    }
}
