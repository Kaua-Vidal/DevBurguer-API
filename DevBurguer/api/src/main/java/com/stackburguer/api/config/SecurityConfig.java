package com.stackburguer.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception{

        return http
                .csrf(csrf -> csrf.disable())   //Desativando para API REST
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))   //API sem estado
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().permitAll())  //Por enquanto, está tudo liberado para teste de login
                .build();
    }
}
