package com.spring.security.samples.basic.auth.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.security.samples.basic.auth.model.Funcao;
import com.spring.security.samples.basic.auth.service.FuncaoLocalService;

@RestController
@RequestMapping(value = "/roles")
@CrossOrigin(value = "http://localhost:3000", maxAge = 3600)
public class FuncaoController {

    @PostMapping
    public ResponseEntity<Funcao> criarFuncao(@RequestBody Funcao funcao) {
        return ResponseEntity.status(HttpStatus.CREATED).body(funcaoLocalService.criarFuncao(funcao));
    }

    @GetMapping
    public ResponseEntity<Iterable<Funcao>> obterFuncoes() {
        return ResponseEntity.status(HttpStatus.OK).body(funcaoLocalService.obterFuncoes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> obterFuncaoPeloId(@PathVariable(value = "id") long id) {
        Funcao funcaoBD = funcaoLocalService.obterFuncaoPeloId(id);

        if (Objects.isNull(funcaoBD)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Função não encontrada.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(funcaoBD);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizarFuncao(@PathVariable(value = "id") long id, @RequestBody Funcao funcao) {
        Funcao funcaoBD = funcaoLocalService.obterFuncaoPeloId(id);

        if (Objects.isNull(funcaoBD)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Função não encontrada.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(funcaoLocalService.atualizarFuncao(id, funcaoBD));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarFuncao(@PathVariable(value = "id") long id) {
        Funcao funcaoBD = funcaoLocalService.obterFuncaoPeloId(id);

        if (Objects.isNull(funcaoBD)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Função não encontrada.");
        }

        funcaoLocalService.deletarFuncao(id);

        return ResponseEntity.status(HttpStatus.OK).body("Função deletada com sucesso.");
    }

    @Autowired
    private FuncaoLocalService funcaoLocalService;

}
