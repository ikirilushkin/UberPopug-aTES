package com.kirilushkin.aa6.task.service;

import com.kirilushkin.aa6.task.mapper.UserMapper;
import com.kirilushkin.aa6.task.model.entity.User;
import com.kirilushkin.aa6.task.model.entity.UserRole;
import com.kirilushkin.aa6.task.model.dto.UserDto;
import com.kirilushkin.aa6.task.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public void updateUser(UserDto userDto) {
        Optional<User> userOptional = userRepository.findById(userDto.getId());
        User user = userMapper.toUser(userDto, userOptional.orElseGet(User::new));
        userRepository.save(user);
    }

    @Override
    public List<UserDto> getAllUsersForAssign() {
        List<User> assigneeUsers = userRepository.findAllByRoleIs(UserRole.USER);
        return assigneeUsers.stream().map(userMapper::toUserDto).toList();
    }
}
