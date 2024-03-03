package com.kirilushkin.aa6.auth.service;

import com.kirilushkin.aa6.auth.model.dto.UserDto;
import java.util.List;
import java.util.UUID;

public interface UserService {

    List<UserDto> getUsers();

    UserDto create(UserDto userDto);

    UserDto getUser(UUID id);

    UserDto updateUser(UUID id, UserDto userDto);

    void delete(UUID id);
}
