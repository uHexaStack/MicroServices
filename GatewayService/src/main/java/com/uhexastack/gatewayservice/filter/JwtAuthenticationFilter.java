package com.uhexastack.gatewayservice.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String USER_ID_HEADER = "X-User-ID";
    private static final String USER_ROLE_HEADER = "X-User-Role";

    @Value("${jwt.secret:aquaengine-jwt-secret-key-2024-very-secure-and-long-secret-key-for-production}")
    private String jwtSecret;

    // Rutas públicas que no requieren autenticación
    private static final List<String> PUBLIC_PATHS = List.of(
            "/api/v1/authentication/sign-in",
            "/api/v1/authentication/sign-up",
            "/api/v1/authentication/refresh",
            "/actuator/health",
            "/actuator/info",
            "/v3/api-docs",
            "/swagger-ui",
            "/swagger-resources",
            "/webjars"
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getPath().value();

        // Verificar si es una ruta pública
        if (isPublicPath(path)) {
            logger.debug("Public path accessed: {}", path);
            return chain.filter(exchange);
        }

        // Extraer el token JWT del header Authorization
        String token = extractTokenFromRequest(request);
        
        if (!StringUtils.hasText(token)) {
            logger.warn("No JWT token found in request to: {}", path);
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        try {
            // Validar y extraer información del token
            Claims claims = validateAndExtractClaims(token);
            
            // Agregar headers con información del usuario
            ServerHttpRequest modifiedRequest = addUserHeaders(request, claims);
            
            logger.debug("JWT token validated successfully for user: {} on path: {}", 
                        claims.getSubject(), path);
            
            return chain.filter(exchange.mutate().request(modifiedRequest).build());
            
        } catch (Exception e) {
            logger.error("JWT token validation failed for path: {}", path, e);
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
    }

    private boolean isPublicPath(String path) {
        return PUBLIC_PATHS.stream().anyMatch(path::startsWith);
    }

    private String extractTokenFromRequest(ServerHttpRequest request) {
        String bearerToken = request.getHeaders().getFirst(AUTHORIZATION_HEADER);
        
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(BEARER_PREFIX.length());
        }
        
        return null;
    }

    private Claims validateAndExtractClaims(String token) {
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private ServerHttpRequest addUserHeaders(ServerHttpRequest request, Claims claims) {
        return request.mutate()
                .header(USER_ID_HEADER, claims.getSubject())
                .header(USER_ROLE_HEADER, claims.get("role", String.class))
                .build();
    }

    @Override
    public int getOrder() {
        return -100; // Alta prioridad para ejecutar antes que otros filtros
    }
} 