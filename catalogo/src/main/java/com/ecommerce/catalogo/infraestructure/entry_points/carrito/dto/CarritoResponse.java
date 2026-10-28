package com.ecommerce.catalogo.infraestructure.entry_points.carrito.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

// el carrito como sale al cliente, con sus lineas y el total
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarritoResponse {

    private Long id;
    private Long idUsuario;
    private String estado;
    private Double total;
    private List<ItemCarritoResponse> items = new ArrayList<>();
}
