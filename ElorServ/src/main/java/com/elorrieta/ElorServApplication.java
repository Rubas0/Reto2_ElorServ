package com.elorrieta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Clase principal de Spring Boot para el servidor ElorServ
 * 
 * Framework Educativo CIFP Elorrieta-Errekamari
 * 
 * Cosas que hace nuestro gran servidor: 
 * - API REST para ElorMov (App Móvil) y ElorMAUI
 * - Servidor TCP para ElorES (App Escritorio)
 * - ORM con Hibernate para gestión de BBDD
 * - Servicios de correo electrónico
 * - Cifrado de contraseñas y comunicaciones
 * 
 */
	
@SpringBootApplication
@EnableAsync // Habilita ejecución asíncrona (para envío de correos)
public class ElorServApplication {

    public static void main(String[] args) {
       
        System.out.println("╔═══════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                                           ║");
        System.out.println("║     ███████╗██╗      ██████╗ ██████╗ ███████╗███████╗██████╗ ██╗   ██╗    ║");
        System.out.println("║     ██╔════╝██║     ██╔═══██╗██╔══██╗██╔════╝██╔════╝██╔══██╗██║   ██║    ║");
        System.out.println("║     █████╗  ██║     ██║   ██║██████╔╝███████╗█████╗  ██████╔╝██║   ██║    ║");
        System.out.println("║     ██╔══╝  ██║     ██║   ██║██╔══██╗╚════██║██╔══╝  ██╔══██╗╚██╗ ██╔╝    ║");
        System.out.println("║     ███████╗███████╗╚██████╔╝██║  ██║███████║███████╗██║  ██║ ╚████╔╝     ║");
        System.out.println("║     ╚══════╝╚══════╝ ╚═════╝ ╚═╝  ╚═╝╚══════╝╚══════╝╚═╝  ╚═╝  ╚═══╝      ║");
        System.out.println("║                                                                           ║");
        System.out.println("║         Framework Educativo CIFP Elorrieta-Errekamari                     ║");
        System.out.println("║                                                                           ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════════════════╝");
        System.out.println();
        
        // Arrancar Spring Boot
        SpringApplication.run(ElorServApplication.class, args);
        
        // Mensajes de inicio
        System.out.println();
        System.out.println("ElorServ iniciado correctamente");
        System.out.println("API REST disponible en: http://localhost:8080/api");
        System.out.println("Servidor TCP disponible en: localhost:9999");
        System.out.println("Base de datos conectada:  jdbc:mysql://localhost:3307/reto2");
        System.out.println();
        System.out.println("Documentación API: http://localhost:8080/api/docs");
        System.out.println("Estado del servidor: http://localhost:8080/actuator/health");
        System.out.println();
    }

    /**
     * Bean para cifrado de contraseñas con BCrypt
     * Se usa en toda la aplicación para hashear passwords
     * 
     * @return PasswordEncoder configurado con BCrypt
     */
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder(11); // El 11 es la fuerza del hash, chagpt me recomienda entre 10 y 12
//    }
}