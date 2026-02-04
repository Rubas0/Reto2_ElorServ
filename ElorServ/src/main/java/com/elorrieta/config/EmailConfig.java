package com.elorrieta.config;

import com.elorrieta.encriptado.CryptAES;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailConfig {

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        
        // Descifrar credenciales usando CryptAES
        CryptAES cryptAES = new CryptAES();
        String credencialesDescifradas = cryptAES.descifrarCredenciales("Clave");
        
        if (credencialesDescifradas != null) {
            String[] partes = credencialesDescifradas.split("\\|\\|\\|");
            if (partes.length == 2) {
                mailSender.setUsername(partes[0]);
                mailSender.setPassword(partes[1]);
            }
        }
        
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        
        return mailSender;
    }
}