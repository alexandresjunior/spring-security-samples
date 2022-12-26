package com.spring.security.samples.basic.auth.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.security.samples.basic.auth.model.Funcao;
import com.spring.security.samples.basic.auth.service.persistence.FuncaoPersistence;

@Service
public class FuncaoLocalService {

    public Funcao criarFuncao(Funcao funcao) {
        return funcaoPersistence.save(funcao);
    }

    public Iterable<Funcao> obterFuncoes() {
        return funcaoPersistence.findAll();
    }

    public Funcao obterFuncaoPeloId(long id) {
        Optional<Funcao> funcao = funcaoPersistence.findById(id);

        if (!funcao.isPresent()) {
            return null;
        }

        return funcao.get();
    }

    public Funcao obterFuncaoPeloNome(String nome) {
        Optional<Funcao> funcao = funcaoPersistence.findByNome(nome);

        if (!funcao.isPresent()) {
            return null;
        }

        return funcao.get();
    }

    public Funcao atualizarFuncao(long id, Funcao funcao) {
        Funcao funcaoBD = funcaoPersistence.findById(id).get();

        funcaoBD.setNome(funcao.getNome());

        return funcaoPersistence.save(funcaoBD);
    }

    public void deletarFuncao(long id) {
        funcaoPersistence.deleteById(id);
    }

    @Autowired
    private FuncaoPersistence funcaoPersistence;

}
