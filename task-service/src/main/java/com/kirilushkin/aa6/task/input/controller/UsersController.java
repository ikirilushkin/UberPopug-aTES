package com.kirilushkin.aa6.task.input.controller;

import com.kirilushkin.aa6.task.model.dto.UserDto;
import com.kirilushkin.aa6.task.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UsersController {

    private final UserService userService;

    @GetMapping
    public List<UserDto> getUsers() {
        return userService.getAllUsersForAssign();
    }
}
