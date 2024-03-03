package com.kirilushkin.aa6.auth.service;

import com.kirilushkin.aa6.auth.model.dto.UserDto;
import com.kirilushkin.aa6.auth.model.entity.User;
import com.kirilushkin.aa6.auth.model.entity.UserRole;
import com.kirilushkin.aa6.auth.output.producer.ProducerImpl;
import com.kirilushkin.aa6.auth.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ProducerImpl producer;

    @Override
    public List<UserDto> getUsers() {
        return userRepository.findAllByDeletedIsFalse().stream()
              .map(this::mapper)
              .toList();
    }

    @Override
    @Transactional
    public UserDto create(UserDto userDto) {
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setDeleted(false);
        user.setRole(UserRole.USER);
        user.setPassword(userDto.getPassword());
        user = userRepository.save(user);
        UserDto created = mapper(user);
        producer.sendUser(created);
        return created;
    }

    @Override
    public UserDto getUser(UUID id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        return mapper(userOptional.get());
    }

    @Override
    public UserDto updateUser(UUID id, UserDto userDto) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        User user = userOptional.get();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setRole(userDto.getRole());
        user = userRepository.save(user);
        UserDto updated = mapper(user);
        if (!updated.getDeleted()) {
            producer.sendUser(updated);
        }
        return updated;
    }

    @Override
    public void delete(UUID id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        User user = userOptional.get();
        user.setDeleted(true);
        userRepository.save(user);
    }

    private UserDto mapper(User user) {
        return UserDto.builder()
              .id(user.getId())
              .firstName(user.getFirstName())
              .lastName(user.getLastName())
              .email(user.getEmail())
              .role(user.getRole())
              .deleted(user.isDeleted())
              .build();
    }
}
