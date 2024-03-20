package com.kirilushkin.aa6.auth.model.event;

import com.kirilushkin.aa6.auth.model.entity.UserRole;
import com.kirilushkin.aa6.auth.model.event.UserCreated.UserCreatedEventData;
import lombok.Getter;
import lombok.Setter;

public class UserCreated extends BaseEvent<UserCreatedEventData> {

    @Getter
    @Setter
    public static class UserCreatedEventData {

        private String publicId;
        private String firstName;
        private String lastName;
        private String email;
        private UserRole role;
    }
}
