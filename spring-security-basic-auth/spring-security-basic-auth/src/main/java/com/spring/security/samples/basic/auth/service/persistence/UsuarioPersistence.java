package com.spring.security.samples.basic.auth.service.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.security.samples.basic.auth.model.Usuario;

@Repository
public interface UsuarioPersistence extends JpaRepository<Usuario, Long> {

    Page<Usuario> findAll(Pageable pageable);

}
