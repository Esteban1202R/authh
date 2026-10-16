package com.ecommerce.catalogo.infraestructure.driver_adapters.external_repository;

import com.ecommerce.catalogo.domain.exception.ValidacionNegocioException;
import com.ecommerce.catalogo.domain.model.Usuario;
import com.ecommerce.catalogo.domain.model.gateway.UsuarioGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

// Adaptador que consume las APIs del microservicio de autenticacion
@Component
@RequiredArgsConstructor
public class UsuarioGatewayImpl implements UsuarioGateway {

    private final RestTemplate restTemplate;

    @Value("${auth.url}")
    private String authUrl;

    @Override
    public Usuario buscarPorIdUsuario(Long idUsuario) {
        try {
            Usuario usuario = restTemplate.getForObject(authUrl + "/" + idUsuario, Usuario.class);

            if (usuario == null) {
                return new Usuario();
            }

            return usuario;
        } catch (HttpClientErrorException.NotFound noEncontrado) {
            return new Usuario();
        } catch (Exception error) {
            throw new RuntimeException("Error al consultar el servicio de autenticacion", error);
        }
    }

    @Override
    public void descontarSaldo(Long idUsuario, Double monto) {
        try {
            // Auth responde 200 tanto si descuenta como si rechaza por regla de negocio:
            // si descuenta devuelve el usuario en JSON, si rechaza devuelve el motivo en texto.
            ResponseEntity<String> respuesta = restTemplate.exchange(
                    authUrl + "/saldo/descontar/" + idUsuario + "/" + monto,
                    HttpMethod.PUT, null, String.class);

            String cuerpo = respuesta.getBody();

            if (cuerpo == null || !cuerpo.trim().startsWith("{")) {
                throw new ValidacionNegocioException(cuerpo);
            }
        } catch (ValidacionNegocioException reglaDeNegocio) {
            throw reglaDeNegocio;
        } catch (HttpClientErrorException error) {
            throw new RuntimeException("No se pudo descontar el saldo: " + error.getResponseBodyAsString());
        } catch (Exception error) {
            throw new RuntimeException("Error al descontar el saldo en autenticacion", error);
        }
    }
}
