package com.kirilushkin.aa6.task.mapper;

import com.kirilushkin.aa6.task.model.entity.User;
import com.kirilushkin.aa6.task.model.dto.UserDto;

public interface UserMapper {

    UserDto toUserDto(User user);

    User toUser(UserDto source, User target);
}
