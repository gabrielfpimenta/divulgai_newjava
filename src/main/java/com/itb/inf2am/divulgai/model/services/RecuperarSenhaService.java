package com.itb.inf2am.divulgai.model.services;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.itb.inf2am.divulgai.model.entity.RecuperarSenha;
import com.itb.inf2am.divulgai.model.entity.Usuario;
import com.itb.inf2am.divulgai.model.repository.RecuperarSenhaRepository;

@Service
public class RecuperarSenhaService {

    private final RecuperarSenhaRepository recuperarSenhaRepository;

    public RecuperarSenhaService(
            RecuperarSenhaRepository recuperarSenhaRepository) {

        this.recuperarSenhaRepository = recuperarSenhaRepository;
    }

    public RecuperarSenha gerarCodigo(Usuario usuario) {

        Optional<RecuperarSenha> codigoAnterior =
                recuperarSenhaRepository.findByUsuarioAndStatusCodigo(
                        usuario,
                        true);

        if (codigoAnterior.isPresent()) {

            RecuperarSenha recuperarSenha = codigoAnterior.get();

            recuperarSenha.setStatusCodigo(false);

            recuperarSenhaRepository.save(recuperarSenha);
        }

        String codigo = String.format("%06d",
                new Random().nextInt(1000000));

        RecuperarSenha recuperarSenha = new RecuperarSenha();

        recuperarSenha.setUsuario(usuario);
        recuperarSenha.setCodigo(codigo);
        recuperarSenha.setDataEnvio(LocalDateTime.now());
        recuperarSenha.setExpiracaoCodigo(
                LocalDateTime.now().plusMinutes(10));
        recuperarSenha.setStatusCodigo(true);

        return recuperarSenhaRepository.save(recuperarSenha);
    }

    public boolean validarCodigo(
            Usuario usuario,
            String codigo) {

        Optional<RecuperarSenha> recuperarSenha =
                recuperarSenhaRepository
                        .findByUsuarioAndCodigoAndStatusCodigo(
                                usuario,
                                codigo,
                                true);

        if (recuperarSenha.isEmpty()) {
            return false;
        }

        if (recuperarSenha.get()
                .getExpiracaoCodigo()
                .isBefore(LocalDateTime.now())) {

            return false;
        }

        return true;
    }

    public void invalidarCodigo(Usuario usuario) {

        recuperarSenhaRepository
                .findByUsuarioAndStatusCodigo(usuario, true)
                .ifPresent(recuperarSenha -> {

                    recuperarSenha.setStatusCodigo(false);

                    recuperarSenhaRepository.save(recuperarSenha);

                });
    }

}