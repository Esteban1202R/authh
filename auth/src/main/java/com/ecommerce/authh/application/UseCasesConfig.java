package com.ecommerce.authh.application;

import com.ecommerce.authh.domain.model.gateway.EncrypterGateway;
import com.ecommerce.authh.domain.model.gateway.UsuarioGateway;
import com.ecommerce.authh.domain.usecase.UsuarioUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCasesConfig {

    @Bean
    public UsuarioUseCase usuarioUseCase(UsuarioGateway usuarioGateway, EncrypterGateway encrypterGateway) {
        return new UsuarioUseCase(usuarioGateway, encrypterGateway);
    }
}