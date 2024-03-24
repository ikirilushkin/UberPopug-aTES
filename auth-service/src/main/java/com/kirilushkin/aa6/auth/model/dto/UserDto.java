package com.kirilushkin.aa6.auth.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kirilushkin.aa6.auth.model.entity.UserRole;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserDto {

    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private UserRole role;
    private Boolean deleted;
    private String password;
    private UUID publicId;

    public static UserDto create() {
        return UserDto.builder()
              .deleted(false)
              .build();
    }
}
