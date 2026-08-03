package com.itb.inf2am.divulgai.model.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarCodigo(String destino, String codigo) {

        SimpleMailMessage email = new SimpleMailMessage();

        email.setTo(destino);
        email.setSubject("Recuperação de senha");

        email.setText("""
                Olá!

                Você solicitou a recuperação da sua senha.

                Seu código é:

                %s

                Este código expira em 10 minutos.

                Caso não tenha solicitado esta recuperação, ignore este e-mail.
                """.formatted(codigo));

        mailSender.send(email);
    }

}