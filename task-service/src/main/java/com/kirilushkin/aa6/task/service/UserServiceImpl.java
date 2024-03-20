package com.kirilushkin.aa6.task.service;

import com.kirilushkin.aa6.task.mapper.UserMapper;
import com.kirilushkin.aa6.task.model.entity.User;
import com.kirilushkin.aa6.task.model.entity.UserRole;
import com.kirilushkin.aa6.task.model.dto.UserDto;
import com.kirilushkin.aa6.task.model.event.UserCreated.UserCreatedEventData;
import com.kirilushkin.aa6.task.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public void create(UserCreatedEventData data) {
        UUID userPublicId = UUID.fromString(data.getPublicId());
        User user = userRepository.findByPublicId(userPublicId).orElse(new User(userPublicId));
        User updated = userMapper.toUser(data, user);
        userRepository.save(updated);
    }

    @Override
    public List<UserDto> getAllUsersForAssign() {
        List<User> assigneeUsers = userRepository.findAllByRoleIs(UserRole.USER);
        return assigneeUsers.stream().map(userMapper::toUserDto).toList();
    }
}
