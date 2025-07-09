package com.uhexastack.profileservice.profiles.infrastructure.authorization.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

@org.springframework.context.annotation.Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/public/**").permitAll() // Endpoints públicos
                .anyRequest().authenticated()              // Todo lo demás requiere JWT válido
            )
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt() // Habilita validación JWT
            );
        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        String secretKey = "TU_SECRETO_COMPARTIDO"; // Usa el mismo valor que en application.yml
        return NimbusJwtDecoder.withSecretKey(
            new javax.crypto.spec.SecretKeySpec(secretKey.getBytes(), "HmacSHA256")
        ).build();
    }
}
