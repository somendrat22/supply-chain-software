package com.supply_chain_easy.sourcing_and_procurement_operations.security;


import com.supply_chain_easy.supply_chain_base_operations.utilities.JwtUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfiguration {
    private JwtUtility jwtUtility;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration =
                new CorsConfiguration();

        configuration.setAllowedOrigins(
                List.of(
                        "http://localhost:5500",
                        "http://127.0.0.1:5500"
                )
        );

        configuration.setAllowedMethods(
                List.of(
                        "GET",
                        "POST",
                        "PUT",
                        "PATCH",
                        "DELETE",
                        "OPTIONS"
                )
        );

        configuration.setAllowedHeaders(
                List.of("*")
        );

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration(
                "/**",
                configuration
        );

        return source;
    }



    @Autowired
    public SecurityConfiguration(JwtUtility jwtUtility) {
        this.jwtUtility = jwtUtility;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.cors(cors -> cors.configurationSource(
                corsConfigurationSource()
        )).csrf((csrf) -> csrf.disable()).authorizeHttpRequests((auth) -> ((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)auth.requestMatchers(new String[]{"/spo/api/v1/emp/login", "/spo/api/v1/procurement-company/on-board", "/error"})).permitAll().anyRequest()).authenticated()).sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).addFilterBefore(new JwtFilter(this.jwtUtility), UsernamePasswordAuthenticationFilter.class);
        return (SecurityFilterChain)http.build();
    }
}