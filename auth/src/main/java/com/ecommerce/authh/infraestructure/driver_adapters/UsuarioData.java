package com.ecommerce.authh.infraestructure.driver_adapters;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "usuarios")

public class UsuarioData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    @Column(length = 40, nullable = false, unique = true)
    private String correo;
    private String clave;
    private String rol;
    private String numerodecelular;
    private Integer edad;
    @Column(nullable = false)
    private Double saldo;

}
