package com.ecommerce.authh.infraestructure.entry_points.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// el login solo necesita estos dos
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    private String correo;
    private String clave;
}
