package com.spring.security.samples.basic.auth.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.security.samples.basic.auth.model.Usuario;

import jakarta.transaction.Transactional;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UsuarioLocalService usuarioLocalService;

    @Transactional
    public Usuario salvar(Usuario usuario) {
        return usuarioLocalService.criarUsuario(usuario);
    }

    public UserDetails autenticar(Usuario usuario) throws Exception {
        UserDetails user = loadUserByUsername(usuario.getEmail());

        boolean senhasBatem = encoder.matches(usuario.getSenha(), new BCryptPasswordEncoder().encode(user.getPassword()).toString());

        if (senhasBatem) {
            return user;
        }

        throw new Exception("Senha Inválida.");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioLocalService.obterUsuarioPeloEmail(username);

        if (Objects.isNull(usuario)) {
            throw new UsernameNotFoundException("Usuário não encontrado na base de dados.");
        }

        String[] roles = usuario.getFuncao().getNome().equalsIgnoreCase("ADMIN") ? new String[] { "ADMIN", "GUEST" }
                : new String[] { "GUEST" };

        return User
                .builder()
                .username(usuario.getEmail())
                .password(usuario.getSenha())
                .roles(roles)
                .build();
    }

}
