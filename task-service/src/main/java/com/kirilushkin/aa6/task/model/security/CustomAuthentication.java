package com.kirilushkin.aa6.task.model.security;

import com.kirilushkin.aa6.task.model.entity.UserRole;
import java.util.List;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public class CustomAuthentication extends JwtAuthenticationToken {

    private final String email;

    public CustomAuthentication(Jwt jwt, UserRole role, String email) {
        super(jwt, List.of(role::name));
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
