package com.ecommerce.catalogo.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Copia local de los datos que catalogo necesita del microservicio de autenticacion
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Usuario {

    private Long id;
    private String nombre;
    private String correo;
    private Double saldo;
}
