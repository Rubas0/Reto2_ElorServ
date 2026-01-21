package com.elorrieta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${mail.from}")
    private String fromEmail;

    @Value("${mail.from.name}")
    private String fromName;

    @Async
    public void sendPasswordResetEmail(String toEmail, String username, String newPassword) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(toEmail);
            message.setSubject("Recuperación de contraseña - ElorServ");

            String emailBody = String.format(
                    "Hola %s,\n\n" +
                            "Has solicitado restablecer tu contraseña.\n\n" +
                            "Tu nueva contraseña temporal es: %s\n\n" +
                            "Por seguridad, te recomendamos cambiarla después de iniciar sesión.\n\n" +
                            "Saludos,\n" +
                            "%s",
                    username, newPassword, fromName
            );

            message.setText(emailBody);

            mailSender.send(message);
            System.out.println("Email enviado correctamente a: " + toEmail);

        } catch (Exception e) {
            System.out.println("Error al enviar email: " + e.getMessage());
        }
    }
}
