package com.elorrieta.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors. UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;

/**
 * Configuración CORS (Cross-Origin Resource Sharing)
 * 
 * Permite que la API REST sea consumida por: 
 * - ElorMov (App Móvil) 
 * - ElorMAUI (App MAUI)
 * 
 * Sin esto, el navegador/app bloqueará las peticiones por seguridad
 */
@Configuration
public class CorsConfig {

    @Value("${cors.allowed-origins}")
    private String allowedOrigins;

    @Value("${cors.allowed-methods}")
    private String allowedMethods;

    @Value("${cors.allowed-headers}")
    private String allowedHeaders;

    @Value("${cors. allow-credentials}")
    private boolean allowCredentials;

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Permitir credenciales (cookies, authorization headers, etc.)
        config.setAllowCredentials(allowCredentials);

        // Orígenes permitidos (Angular, App Móvil, etc.)
        List<String> origins = Arrays.asList(allowedOrigins.split(","));
        config.setAllowedOrigins(origins);

        // Métodos HTTP permitidos
        List<String> methods = Arrays.asList(allowedMethods.split(","));
        config.setAllowedMethods(methods);

        // Headers permitidos
        config.addAllowedHeader(allowedHeaders);

        // Headers expuestos (visibles para el cliente)
        config.addExposedHeader("Authorization");
        config.addExposedHeader("Content-Type");

        // Aplicar configuración a todos los endpoints
        source.registerCorsConfiguration("/api/**", config);

        System.out.println("✅ CORS configurado para orígenes: " + allowedOrigins);

        return new CorsFilter(source);
    }
}