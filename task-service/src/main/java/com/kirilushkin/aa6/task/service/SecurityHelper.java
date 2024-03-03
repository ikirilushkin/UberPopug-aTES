package com.kirilushkin.aa6.task.service;

import com.kirilushkin.aa6.task.model.entity.User;
import com.kirilushkin.aa6.task.model.exception.NotFoundException;
import com.kirilushkin.aa6.task.model.security.CustomAuthentication;
import com.kirilushkin.aa6.task.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityHelper {

    private final UserRepository userRepository;

    public User getCurrentUser() {
        CustomAuthentication authentication = (CustomAuthentication) SecurityContextHolder.getContext()
              .getAuthentication();
        String email = authentication.getEmail();
        return userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User", email));
    }
}
