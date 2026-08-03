package com.itb.inf2am.divulgai.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.itb.inf2am.divulgai.model.entity.RecuperarSenha;
import com.itb.inf2am.divulgai.model.entity.Usuario;

@Repository
public interface RecuperarSenhaRepository
        extends JpaRepository<RecuperarSenha, Long> {

    Optional<RecuperarSenha> findByUsuarioAndStatusCodigo(
            Usuario usuario,
            Boolean statusCodigo);

    Optional<RecuperarSenha> findByUsuarioAndCodigoAndStatusCodigo(
            Usuario usuario,
            String codigo,
            Boolean statusCodigo);

}