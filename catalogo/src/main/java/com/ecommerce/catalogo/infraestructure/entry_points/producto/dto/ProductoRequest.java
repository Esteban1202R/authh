package com.ecommerce.catalogo.infraestructure.entry_points.producto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// lo que entra por el json al crear o actualizar un producto
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoRequest {

    private Long id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private Integer stock;
    private String imagenUrl;
}
