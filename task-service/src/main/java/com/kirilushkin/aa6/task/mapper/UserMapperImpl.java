package com.kirilushkin.aa6.task.mapper;

import com.kirilushkin.aa6.task.model.entity.User;
import com.kirilushkin.aa6.task.model.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto toUserDto(User user) {
        if (user == null) {
            return null;
        }
        return UserDto.builder()
              .id(user.getId())
              .email(user.getEmail())
              .role(user.getRole())
              .firstName(user.getFirstName())
              .lastName(user.getLastName())
              .build();
    }

    @Override
    public User toUser(UserDto source, User target) {
        if (target == null) {
            target = new User();
        }
        if (source.getId() != null) {
            target.setId(source.getId());
        }
        target.setFirstName(source.getFirstName());
        target.setLastName(source.getLastName());
        target.setEmail(source.getEmail());
        target.setRole(source.getRole());
        return target;
    }
}
