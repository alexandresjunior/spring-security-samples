package com.spring.security.samples.basic.auth.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.spring.security.samples.basic.auth.model.Usuario;
import com.spring.security.samples.basic.auth.service.persistence.UsuarioPersistence;

@Service
public class UsuarioLocalService {

    public Usuario criarUsuario(Usuario usuario) {
        return usuarioPersistence.save(usuario);
    }

    public Page<Usuario> obterUsuarios(Pageable pageable) {
        return usuarioPersistence.findAll(pageable);
    }

    public Usuario obterUsuarioPeloId(long id) {
        Optional<Usuario> usuario = usuarioPersistence.findById(id);

        if (!usuario.isPresent()) {
            return null;
        }

        return usuario.get();
    }

    public Usuario obterUsuarioPeloEmail(String email) {
        Optional<Usuario> usuario = usuarioPersistence.findByEmail(email);

        if (!usuario.isPresent()) {
            return null;
        }

        return usuario.get();
    }

    public Usuario atualizarUsuario(long id, Usuario usuario) {
        Usuario usuarioBD = usuarioPersistence.findById(id).get();

        usuarioBD.setNome(usuario.getNome());
        usuarioBD.setEmail(usuario.getEmail());
        usuarioBD.setSenha(usuario.getSenha());

        return usuarioPersistence.save(usuarioBD);
    }

    public void deletarUsuario(long id) {
        usuarioPersistence.deleteById(id);
    }

    @Autowired
    private UsuarioPersistence usuarioPersistence;

}
