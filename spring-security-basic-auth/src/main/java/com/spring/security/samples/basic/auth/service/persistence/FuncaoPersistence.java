package com.spring.security.samples.basic.auth.service.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.security.samples.basic.auth.model.Funcao;

@Repository
public interface FuncaoPersistence extends JpaRepository<Funcao, Long> {

    Optional<Funcao> findByNome(String nome);

}
