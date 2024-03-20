package com.kirilushkin.aa6.audit.config;

import com.kirilushkin.aa6.audit.model.security.JwtAuthenticationConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Value("${popug.auth.keySetURI}")
    private String keySetUri;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.oauth2ResourceServer(
              configurer -> configurer.jwt(
                    jwtConfigurer -> jwtConfigurer
                          .jwkSetUri(keySetUri)
                          .jwtAuthenticationConverter(new JwtAuthenticationConverter())
              )
        );
        http.authorizeHttpRequests(
              c -> c.anyRequest().authenticated()
        );
        return http.build();
    }
}
