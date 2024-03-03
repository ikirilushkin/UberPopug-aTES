package com.kirilushkin.aa6.task.input.listener;

import com.kirilushkin.aa6.task.model.dto.UserDto;
import com.kirilushkin.aa6.task.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class Listener {

    private final UserService userService;

    @KafkaListener(topics = "users-stream")
    public void onUserUpdated(UserDto userDto) {
        userService.updateUser(userDto);
    }
}
