package com.ecommerce.authh.infraestructure.driver_adapters;

import com.ecommerce.authh.domain.model.Usuario;
import com.ecommerce.authh.domain.model.gateway.UsuarioGateway;
import com.ecommerce.authh.infraestructure.mapper.MapperUsuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor

public class UsuarioDataGatewayImpl implements UsuarioGateway {

    private final MapperUsuario mapperUsuario;
    private final UsuarioDataJpaRepository repository;

    @Override
    public Usuario guardar(Usuario usuario) {

        UsuarioData usuarioData = mapperUsuario.toData(usuario);
        return (Usuario) mapperUsuario.toUsuario(repository.save(usuarioData));
    }

    @Override
    public Usuario guardarUsuario(Usuario usuario) {
        return guardar(usuario);
    }

    @Override
    public Usuario buscarPorIdUsuario(String idUsuario) {
        return repository.findById(idUsuario)
                .map(mapperUsuario::toUsuario)
                .orElse(new Usuario());
    }

    @Override
    public Usuario ActualizarUsuario(Usuario usuario) {
        return guardar(usuario);
    }

    @Override
    public void eliminarPorUsuario(String idUsuario) {
        repository.deleteById(idUsuario);
    }

    @Override
    public Usuario buscarPorCorreo(String correo) {
        return repository.findFirstByCorreo(correo)
                .map(mapperUsuario::toUsuario)
                .orElse(new Usuario());
    }





}
