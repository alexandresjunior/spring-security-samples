package com.spring.security.samples.basic.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.security.samples.basic.auth.model.Usuario;
import com.spring.security.samples.basic.auth.service.UsuarioLocalService;

@RestController
@RequestMapping(value = "/users")
@CrossOrigin(value = "http://localhost:3000")
public class UsuarioController {

    @PostMapping
    public Usuario criarUsuario(@RequestBody Usuario usuario) {
        return usuarioLocalService.criarUsuario(usuario);
    }

    @GetMapping
    public Page<Usuario> obterUsuarios(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Order.asc("nome")));

        return usuarioLocalService.obterUsuarios(pageable);
    }

    @GetMapping("/{id}")
    public Usuario obterUsuarioPeloId(@PathVariable(value = "id") long id) {
        return usuarioLocalService.obterUsuarioPeloId(id);
    }

    @PutMapping("/{id}")
    public Usuario atualizarUsuario(@PathVariable(value = "id") long id, @RequestBody Usuario usuario) {
        return usuarioLocalService.atualizarUsuario(id, usuario);
    }

    @DeleteMapping("/{id}")
    public void deletarUsuario(@PathVariable(value = "id") long id) {
        usuarioLocalService.deletarUsuario(id);
    }

    @Autowired
    private UsuarioLocalService usuarioLocalService;

}
