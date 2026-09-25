package com.ecommerce.authh.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Usuario {

    private Long id;
    private String nombre;
    private String correo;
    private String clave;
    private String rol;
    private String numerodecelular;
    private Integer edad;

    }