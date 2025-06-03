package com.orderize.backoffice_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendGeneratedPasswordEmail(String to, String newPassword) {
        if (mailSender == null) {
            System.err.println("ERRO FATAL: JavaMailSender não foi injetado. Verifique a configuração do Spring Mail.");
            throw new IllegalStateException("JavaMailSender bean is null. Cannot send email.");
        }

        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setSubject("Sua Nova Senha - Seu Aplicativo");
//            message.setText("Seu token de redefinição de senha é: "+ newPassword);

            String emailContent = "Olá,\n\n"
                    + "Sua senha foi redefinida com sucesso.\n"
                    + "Sua nova senha é: " + newPassword + "\n\n"
                    + "Recomendamos que você anote sua nova senha em um local seguro e faça login com ela no aplicativo.\n\n"
                    + "Se você não solicitou isso, por favor, entre em contato conosco imediatamente.\n\n"
                    + "Atenciosamente,\nEquipe Orderize";

            message.setText(emailContent);

            System.out.println("DEBUG: Tentando enviar email para: " + to + " do remetente: " + fromEmail);

            mailSender.send(message);

            System.out.println("DEBUG: Email de redefinição de senha enviado com sucesso para: " + to);

        }catch (Exception e) {
            System.err.println("ERRO ao enviar e-mail: " + e.getMessage());
            e.printStackTrace();
        }
    }


}
