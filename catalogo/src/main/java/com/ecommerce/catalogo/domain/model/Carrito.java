package com.ecommerce.catalogo.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Carrito {

    private Long id;
    private Long idUsuario;
    private String estado;
    private Double total;
    private List<ItemCarrito> items = new ArrayList<>();
}
