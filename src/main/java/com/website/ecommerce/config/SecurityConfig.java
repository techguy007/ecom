package com.website.ecommerce.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
// import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer.AuthorizationManagerRequestMatcherRegistry;
// import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer.AuthorizedUrl;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.website.ecommerce.filter.JwtAuthenticationFilter;
import com.website.ecommerce.service.UserDetailsServiceImp;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserDetailsServiceImp UserDetailsServiceImp;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(UserDetailsServiceImp userDetailsServiceImp,
            JwtAuthenticationFilter jwtAuthenticationFilter) {
        UserDetailsServiceImp = userDetailsServiceImp;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
         return http.csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(req -> req
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll() // Permit access to static resources
                .requestMatchers(HttpMethod.GET).permitAll() // Allow all GET requests
                .requestMatchers("/admin_only/**").hasAuthority("ADMIN") // Require ADMIN authority for admin_only endpoints
                .requestMatchers("/**").permitAll() // Permit access to login and register endpoints
                .anyRequest().authenticated() // Require authentication for all other requests
            )
            .userDetailsService(UserDetailsServiceImp) // Set custom UserDetailsService
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Set session management policy to stateless
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // Add JWT authentication filter before UsernamePasswordAuthenticationFilter
            .build();
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }
}
