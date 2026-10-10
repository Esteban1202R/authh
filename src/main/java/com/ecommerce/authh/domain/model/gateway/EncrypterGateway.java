package com.ecommerce.authh.domain.model.gateway;

public interface EncrypterGateway {

    String encrypt(String clave);

    Boolean checkPass(String claveUsuario, String claveBD);
}
