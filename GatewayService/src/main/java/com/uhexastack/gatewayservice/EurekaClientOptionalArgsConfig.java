package com.uhexastack.gatewayservice;

                import org.springframework.cloud.netflix.eureka.EurekaClientConfigBean;
                import org.springframework.context.annotation.Bean;
                import org.springframework.context.annotation.Configuration;

                @Configuration
                public class EurekaClientOptionalArgsConfig {

                    @Bean
                    public EurekaClientConfigBean eurekaClientConfigBean() {
                        return new EurekaClientConfigBean();
                    }
                }