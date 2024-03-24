package com.kirilushkin.aa6.auth.model.event;

import com.kirilushkin.aa6.auth.model.entity.UserRole;
import com.kirilushkin.aa6.auth.model.event.UserRoleChanged.UserRoleChangedEventData;
import lombok.Getter;
import lombok.Setter;

public class UserRoleChanged extends BaseEvent<UserRoleChangedEventData> {

    @Getter
    @Setter
    public static class UserRoleChangedEventData {

        private String publicId;
        private UserRole role;
    }
}
