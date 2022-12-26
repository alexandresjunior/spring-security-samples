package com.spring.security.samples.basic.auth.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import org.springframework.web.server.ResponseStatusException;

import com.spring.security.samples.basic.auth.configuration.security.JwtService;
import com.spring.security.samples.basic.auth.dto.CredenciaisDTO;
import com.spring.security.samples.basic.auth.dto.TokenDTO;
import com.spring.security.samples.basic.auth.dto.UsuarioDTO;
import com.spring.security.samples.basic.auth.model.Funcao;
import com.spring.security.samples.basic.auth.model.Usuario;
import com.spring.security.samples.basic.auth.service.FuncaoLocalService;
import com.spring.security.samples.basic.auth.service.UsuarioLocalService;
import com.spring.security.samples.basic.auth.service.UsuarioService;

@RestController
@RequestMapping(value = "/users")
@CrossOrigin(value = "http://localhost:3000", maxAge = 3600)
public class UsuarioController {

    @PostMapping
    public ResponseEntity<Object> criarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();

        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setSenha(new BCryptPasswordEncoder().encode(usuarioDTO.getSenha()));

        Funcao funcao = funcaoLocalService.obterFuncaoPeloNome(usuarioDTO.getFuncao());

        if (Objects.isNull(funcao)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Função não encontrada.");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioLocalService.criarUsuario(usuario));
    }

    @GetMapping
    public ResponseEntity<Page<Usuario>> obterUsuarios(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Order.asc("nome")));

        return ResponseEntity.status(HttpStatus.OK).body(usuarioLocalService.obterUsuarios(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> obterUsuarioPeloId(@PathVariable(value = "id") long id) {
        Usuario usuario = usuarioLocalService.obterUsuarioPeloId(id);

        if (Objects.isNull(usuario)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(usuarioLocalService.obterUsuarioPeloId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizarUsuario(@PathVariable(value = "id") long id,
            @RequestBody Usuario usuarioDTO) {
        Usuario usuario = usuarioLocalService.obterUsuarioPeloId(id);

        if (Objects.isNull(usuario)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(usuarioLocalService.atualizarUsuario(id, usuarioDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarUsuario(@PathVariable(value = "id") long id) {
        Usuario usuario = usuarioLocalService.obterUsuarioPeloId(id);

        if (Objects.isNull(usuario)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        }

        usuarioLocalService.deletarUsuario(id);

        return ResponseEntity.status(HttpStatus.OK).body("Usuário deletado com sucesso.");
    }

    @PostMapping("/auth")
    public TokenDTO autenticar(@RequestBody CredenciaisDTO credenciaisDTO) {
        try {
            Usuario usuario = Usuario.builder()
                    .email(credenciaisDTO.getLogin())
                    .senha(credenciaisDTO.getSenha()).build();

            usuarioService.autenticar(usuario);

            String token = jwtService.gerarToken(usuario);

            return new TokenDTO(usuario.getEmail(), token);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @Autowired
    private FuncaoLocalService funcaoLocalService;

    @Autowired
    private UsuarioLocalService usuarioLocalService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtService jwtService;

}
