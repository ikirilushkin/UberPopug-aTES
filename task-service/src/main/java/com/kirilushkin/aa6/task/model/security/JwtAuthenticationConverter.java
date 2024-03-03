package com.kirilushkin.aa6.task.model.security;

import com.kirilushkin.aa6.task.model.entity.UserRole;
import com.kirilushkin.aa6.task.model.security.CustomAuthentication;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.jwt.Jwt;

public class JwtAuthenticationConverter implements Converter<Jwt, CustomAuthentication> {

    @Override
    public CustomAuthentication convert(Jwt source) {
        UserRole role = UserRole.valueOf((String) source.getClaims().get("role"));
        String email = (String) source.getClaims().get("sub");
        return new CustomAuthentication(source, role, email);
    }
}
