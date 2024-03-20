package com.kirilushkin.aa6.accounting.input;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kirilushkin.SchemaValidator;
import com.kirilushkin.aa6.accounting.model.event.BaseEvent;
import com.kirilushkin.aa6.accounting.model.event.TaskAdded;
import com.kirilushkin.aa6.accounting.model.event.TaskCreated;
import com.kirilushkin.aa6.accounting.model.event.UserCreated;
import com.kirilushkin.aa6.accounting.service.AccountService;
import com.kirilushkin.aa6.accounting.service.TaskService;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventConsumerImpl implements EventConsumer {

    private final AccountService accountService;
    private final TaskService taskService;
    private final SchemaValidator schemaValidator;
    private final ObjectMapper objectMapper;

    @Override
    @KafkaListener(topics = "users-stream")
    public void onUserCreated(UserCreated event) {
        receive(event, "auth/UserCreated/1.json", o -> accountService.saveAccount(event.getData()));
    }

    @Override
    public void onTaskCreated(TaskCreated event) {
        taskService.createTask(event.getData());
    }

    @Override
    public void onTaskAdded(TaskAdded event) {
        taskService.addTaskToUser(event.getData());
    }

    private <T extends BaseEvent> void receive(T event, String schema, Consumer<Object> consumer) {
        try {
            String msg = objectMapper.writeValueAsString(event);
            var isValid = schemaValidator.validate(msg, schema);
            if (isValid) {
                consumer.accept(event.getData());
            } else {
                throw new IllegalArgumentException(String.format("Schema %s is invalid for message %s", schema, msg));
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
