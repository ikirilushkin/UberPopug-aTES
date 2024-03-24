package com.kirilushkin.aa6.auth.output.producer;

import com.kirilushkin.aa6.auth.model.dto.UserDto;

public interface Producer {

    void sendUser(UserDto userDto);

    void sendUserRoleChanged(UserDto userDto);
}
