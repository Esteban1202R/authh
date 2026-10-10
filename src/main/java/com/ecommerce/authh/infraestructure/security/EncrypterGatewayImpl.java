package com.ecommerce.authh.infraestructure.security;

import com.ecommerce.authh.domain.model.gateway.EncrypterGateway;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EncrypterGatewayImpl implements EncrypterGateway {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public String encrypt(String clave) {
        return encoder.encode(clave);
    }

    @Override
    public Boolean checkPass(String claveUsuario, String claveBD) {
        return encoder.matches(claveUsuario, claveBD);
    }
}
