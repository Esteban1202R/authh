package com.ecommerce.catalogo.infraestructure.entry_points.producto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// lo que sale al cliente
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoResponse {

    private Long id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private Integer stock;
    private String imagenUrl;
}
