package com.kirilushkin.aa6.task.service;

import com.kirilushkin.aa6.task.model.dto.UserDto;
import java.util.List;

public interface UserService {

    void updateUser(UserDto userDto);

    List<UserDto> getAllUsersForAssign();
}
