package com.uhexastack.gatewayservice.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtPropagationFilter implements GlobalFilter, Ordered {

    private static final Logger logger = LoggerFactory.getLogger(JwtPropagationFilter.class);
    private static final String AUTHORIZATION_HEADER = "Authorization";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getPath().value();

        // Solo procesar rutas que no sean públicas
        if (isPublicPath(path)) {
            return chain.filter(exchange);
        }

        // Obtener el token JWT del header original
        String authHeader = request.getHeaders().getFirst(AUTHORIZATION_HEADER);
        
        if (StringUtils.hasText(authHeader)) {
            // Crear una nueva request con el header de autorización
            ServerHttpRequest modifiedRequest = request.mutate()
                    .header(AUTHORIZATION_HEADER, authHeader)
                    .build();

            logger.debug("Propagating JWT token to service for path: {}", path);
            
            return chain.filter(exchange.mutate().request(modifiedRequest).build());
        }

        return chain.filter(exchange);
    }

    private boolean isPublicPath(String path) {
        return path.startsWith("/api/v1/**") ||
               path.startsWith("/actuator/") ||
               path.startsWith("/registry/") ||
               path.startsWith("/v3/api-docs") ||
               path.startsWith("/swagger-ui") ||
               path.startsWith("/swagger-resources") ||
               path.startsWith("/webjars");
    }

    @Override
    public int getOrder() {
        return -50; // Ejecutar después del JwtAuthenticationFilter pero antes que otros filtros
    }
} 