package br.com.cwi.crescer.api.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EnviarEmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviar(String email, String assunto, String corpo) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(email);
        message.setSubject(assunto);
        message.setText(corpo);

        mailSender.send(message);
    }
}
