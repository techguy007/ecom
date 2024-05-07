package com.website.ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.website.ecommerce.aspect.AuthenticationLoggingAspect;
import com.website.ecommerce.aspect.OrderEmailAspect;

@Configuration
@EnableAspectJAutoProxy
public class AspectConfig {

    @Bean
    public AuthenticationLoggingAspect loggingAspect() {
        return new AuthenticationLoggingAspect();
    }

    @Bean
    public OrderEmailAspect orderEmailAspect() {
        return new OrderEmailAspect();
    }

}