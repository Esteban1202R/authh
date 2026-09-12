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
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String nombre;
    @Column(length = 20)
    private String correo;
    @Column(length = 12, nullable = false,unique = true)
    private String clave;
    private String rol;
    private String numerodecelular;
    private Integer edad;



}
