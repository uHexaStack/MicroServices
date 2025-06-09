package com.uhexastack.orderservice.order.infrastructure.eureka.config;

import com.netflix.discovery.AbstractDiscoveryClientOptionalArgs;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EurekaClientConfig {

    @SuppressWarnings("rawtypes")
    @Bean
    public AbstractDiscoveryClientOptionalArgs discoveryClientOptionalArgs() {
        return new AbstractDiscoveryClientOptionalArgs() {
        };
    }
}