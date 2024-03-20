package com.kirilushkin.aa6.task.input.consumer;

import com.kirilushkin.aa6.task.model.event.UserCreated;
import com.kirilushkin.aa6.task.model.event.UserRoleChanged;
import com.kirilushkin.aa6.task.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConsumerImpl implements Consumer {

    private final UserService userService;

    @Override
    @KafkaListener(topics = "users-stream")
    public void onUserCreated(UserCreated event) {
        userService.create(event.getData());
    }

    @Override
    public void onUserRoleChanged(UserRoleChanged event) {
        // todo reassign task if necessary
    }
}
