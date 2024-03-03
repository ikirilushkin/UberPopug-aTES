package com.kirilushkin.aa6.auth.controller;

import com.kirilushkin.aa6.auth.model.dto.UserDto;
import com.kirilushkin.aa6.auth.model.entity.UserRole;
import com.kirilushkin.aa6.auth.service.UserService;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class UsersController {

    private final UserService userService;

    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR')")
    @GetMapping(value = "/users")
    public String getUsers(Model model) {
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("newUser", UserDto.create());
        return "users";
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR')")
    @PostMapping(value = "/users")
    public String createUser(@ModelAttribute UserDto user, Model model) {
        userService.create(user);
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("newUser", UserDto.create());
        return "users";
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR')")
    @GetMapping(value = "/users/{id}")
    public String getUser(@PathVariable("id") UUID id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        model.addAttribute("roles", List.of(UserRole.values()));
        return "user";
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR')")
    @PostMapping(value = "/users/{id}")
    public String updateUser(@PathVariable("id") UUID id, @ModelAttribute UserDto user) {
        userService.updateUser(id, user);
        return "redirect:/users";
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR')")
    @DeleteMapping(value = "/users/{id}")
    public String deleteUser(@PathVariable("id") UUID id) {
        userService.delete(id);
        return "redirect:/users";
    }
}
