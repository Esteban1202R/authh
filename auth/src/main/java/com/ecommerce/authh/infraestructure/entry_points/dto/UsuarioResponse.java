package com.ecommerce.authh.infraestructure.entry_points.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// lo que sale, sin la clave
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponse {

    private Long id;
    private String nombre;
    private String correo;
    private String rol;
    private String numerodecelular;
    private Integer edad;
    private Double saldo;
}
