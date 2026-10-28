package com.ecommerce.authh.infraestructure.entry_points.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// lo que entra por el json, sin saldo para que no lo puedan mandar
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRequest {

    private Long id;
    private String nombre;
    private String correo;
    private String clave;
    private String rol;
    private String numerodecelular;
    private Integer edad;
}
