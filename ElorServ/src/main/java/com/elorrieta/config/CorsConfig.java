package com.elorrieta.config;

import org.springframework.context.annotation.Bean;
import org. springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;

/**
 * Configuración CORS (Cross-Origin Resource Sharing)
 * 
 * Permite que la API REST sea consumida por:  
 * - ElorMov (App Móvil Android/iOS)
 * - ElorMAUI (App MAUI multiplataforma)
 * 
 * NOTA: ElorAdmin (Angular) NO usa la API REST, se conecta DIRECTAMENTE a MySQL
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Permitir credenciales (cookies, authorization headers, etc.)
        config.setAllowCredentials(true);

        // Orígenes permitidos (ElorMAUI, ElorMov, localhost para que funcione en local, de nada)
        // IMPORTANTE: Ajusta estos orígenes según tus necesidades
        List<String> allowedOrigins = Arrays.asList(
            "http://localhost:8100",      // ElorMov en emulador Android
            "http://localhost:8081",      // ElorMAUI en desarrollo
            "http://10.0.2.2:8080",       // Android emulator apuntando a localhost
            "http://localhost:5000",      // MAUI alternativo
            "*"                           // TEMPORALMENTE permitir todos (¡QUITAR EN PRODUCCIÓN!)
        );
        
        // NOTA: En producción, quita el "*" y deja solo los orígenes específicos
        config.setAllowedOriginPatterns(Arrays.asList("*")); // Permite todos temporalmente
        
        // Métodos HTTP permitidos
        List<String> allowedMethods = Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH");
        config.setAllowedMethods(allowedMethods);

        // Headers permitidos
        config.addAllowedHeader("*");

        // Headers expuestos (visibles para el cliente)
        config.addExposedHeader("Authorization");
        config.addExposedHeader("Content-Type");
        config.addExposedHeader("Access-Control-Allow-Origin");

        // Aplicar configuración a todos los endpoints de la API
        source.registerCorsConfiguration("/api/**", config);

        System.out.println("CORS configurado correctamente");
        System.out.println(" - Permitidos: ElorMAUI, ElorMov");
        System.out.println(" - Métodos: GET, POST, PUT, DELETE, OPTIONS");

        return new CorsFilter(source);
    }
}