package com.elorrieta;

import com.elorrieta.tcpServer.SocketTcp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;


/**
 * Clase principal de Spring Boot para el servidor ElorServ
 * <p>
 * Framework Educativo CIFP Elorrieta-Errekamari
 * <p>
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
        // Arrancar Spring Boot
        SpringApplication.run(ElorServApplication.class, args);

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
        
        // Arrancar el servidor TCP en un hilo aparte
        Thread tcpThread = new Thread(new SocketTcp());
        tcpThread.start();

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

}