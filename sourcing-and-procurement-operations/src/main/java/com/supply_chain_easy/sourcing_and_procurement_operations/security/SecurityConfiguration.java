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

@Configuration
public class SecurityConfiguration {
    private JwtUtility jwtUtility;

    @Autowired
    public SecurityConfiguration(JwtUtility jwtUtility) {
        this.jwtUtility = jwtUtility;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf) -> csrf.disable()).authorizeHttpRequests((auth) -> ((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)auth.requestMatchers(new String[]{"/spo/api/v1/emp/login", "/spo/api/v1/procurement-company/on-board", "/error"})).permitAll().anyRequest()).authenticated()).sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).addFilterBefore(new JwtFilter(this.jwtUtility), UsernamePasswordAuthenticationFilter.class);
        return (SecurityFilterChain)http.build();
    }
}