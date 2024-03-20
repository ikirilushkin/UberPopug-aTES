package com.kirilushkin.aa6.audit.model.security;

import com.kirilushkin.aa6.audit.model.entity.UserRole;
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
