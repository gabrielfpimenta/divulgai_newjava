package com.itb.inf2am.divulgai.model.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void enviarCodigo(String destinatario, String codigo) {
        String assunto = "Seu código de recuperação - DivulgAí";
        
        String htmlMensagem = """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
            </head>
            <body style="margin: 0; padding: 0; background-color: #f4f4f7; font-family: Arial, sans-serif;">
                <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="100%%" style="background-color: #f4f4f7; padding: 40px 0;">
                    <tr>
                        <td align="center">
                            <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="500" style="background-color: #ffffff; border-radius: 8px; padding: 40px; box-shadow: 0 4px 10px rgba(0, 0, 0, 0.05); text-align: center;">
                                <tr>
                                    <td>
                                        <div style="font-size: 24px; font-weight: bold; color: #F05221; margin-bottom: 24px;">
                                            DivulgAí
                                        </div>
                                        <h1 style="font-size: 20px; color: #111111; margin-top: 0; margin-bottom: 16px;">
                                            Redefinição de Senha
                                        </h1>
                                        <p style="font-size: 14px; color: #555555; line-height: 1.6; margin-top: 0; margin-bottom: 24px;">
                                            Recebemos uma solicitação para redefinir a senha da sua conta. Utilize o código de verificação abaixo:
                                        </p>
                                        
                                        <div style="background-color: #fff3f0; border: 2px dashed #F05221; border-radius: 6px; padding: 16px; font-size: 32px; font-weight: bold; color: #F05221; letter-spacing: 6px; margin-bottom: 24px; display: inline-block;">
                                            %s
                                        </div>
                                        
                                        <p style="font-size: 14px; color: #555555; line-height: 1.6; margin-top: 0; margin-bottom: 24px;">
                                            Este código é válido por <strong>10 minutos</strong>. Se você não solicitou isso, pode ignorar este e-mail com segurança.
                                        </p>
                                        
                                        <div style="font-size: 12px; color: #999999; margin-top: 32px; border-top: 1px solid #eeeeee; padding-top: 16px;">
                                            &copy; 2026 DivulgAí. Todos os direitos reservados.
                                        </div>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
            </body>
            </html>
        """.formatted(codigo);

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            
            helper.setTo(destinatario);
            helper.setSubject(assunto);
            helper.setText(htmlMensagem, true); // O 'true' garante o envio como HTML formatado

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Erro ao enviar o e-mail HTML: " + e.getMessage(), e);
        }
    }
}