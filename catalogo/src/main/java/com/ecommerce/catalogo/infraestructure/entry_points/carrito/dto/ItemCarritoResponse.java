package com.ecommerce.catalogo.infraestructure.entry_points.carrito.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// cada linea del carrito como sale al cliente
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemCarritoResponse {

    private Long id;
    private Long idProducto;
    private String nombreProducto;
    private Integer cantidad;
    private Double precioUnitario;
    private Double subtotal;
}
