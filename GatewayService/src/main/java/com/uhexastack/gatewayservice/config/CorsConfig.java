package com.uhexastack.gatewayservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        
        // Permitir todos los orígenes (en producción, especificar dominios específicos)
        corsConfig.setAllowedOriginPatterns(List.of("*"));
        
        // Métodos HTTP permitidos
        corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        
        // Headers permitidos
        corsConfig.setAllowedHeaders(Arrays.asList(
                "Origin",
                "Content-Type",
                "Accept",
                "Authorization",
                "X-Requested-With",
                "X-User-ID",
                "X-User-Role",
                "Cache-Control"
        ));
        
        // Headers expuestos al cliente
        corsConfig.setExposedHeaders(Arrays.asList(
                "Authorization",
                "X-User-ID",
                "X-User-Role"
        ));
        
        // Permitir credenciales
        corsConfig.setAllowCredentials(true);
        
        // Tiempo de vida del preflight request
        corsConfig.setMaxAge(3600L);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);
        
        return new CorsWebFilter(source);
    }
} 