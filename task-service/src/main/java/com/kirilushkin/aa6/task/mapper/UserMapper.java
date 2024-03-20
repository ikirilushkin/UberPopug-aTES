package com.kirilushkin.aa6.task.mapper;

import com.kirilushkin.aa6.task.model.entity.User;
import com.kirilushkin.aa6.task.model.dto.UserDto;
import com.kirilushkin.aa6.task.model.event.UserCreated.UserCreatedEventData;

public interface UserMapper {

    UserDto toUserDto(User user);

    User toUser(UserCreatedEventData data, User target);
}
