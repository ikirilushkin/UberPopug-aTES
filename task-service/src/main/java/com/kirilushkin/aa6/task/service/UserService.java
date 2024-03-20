package com.kirilushkin.aa6.task.service;

import com.kirilushkin.aa6.task.model.dto.UserDto;
import com.kirilushkin.aa6.task.model.event.UserCreated.UserCreatedEventData;
import java.util.List;

public interface UserService {

    void create(UserCreatedEventData data);

    List<UserDto> getAllUsersForAssign();
}
