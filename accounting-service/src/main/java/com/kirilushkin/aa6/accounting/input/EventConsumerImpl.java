package com.kirilushkin.aa6.accounting.input;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kirilushkin.SchemaValidator;
import com.kirilushkin.aa6.accounting.model.event.BaseEvent;
import com.kirilushkin.aa6.accounting.model.event.TaskAdded;
import com.kirilushkin.aa6.accounting.model.event.TaskAssigned;
import com.kirilushkin.aa6.accounting.model.event.TaskCompleted;
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
    public void onUserSaved(UserCreated event) {
        receive(event, "auth/UserCreated/1.json", o -> accountService.saveAccount(event.getData()));
    }

    @Override
    @KafkaListener(topics = "tasks-stream")
    public void onTaskSaved(TaskCreated event) {
        receive(event, "task/TaskCreated/1.json", o -> taskService.createTask(event.getData()));
    }

    @Override
    @KafkaListener(topics = "tasks-added")
    public void onTaskAdded(TaskAdded event) {
        receive(event, "task/TaskAdded/1.json", o -> taskService.addTaskToUser(event.getData()));
    }

    @Override
    @KafkaListener(topics = "tasks-assigned")
    public void onTaskAssigned(TaskAssigned event) {
        receive(event, "task/TaskAssigned/1.json", o -> taskService.assignTaskToUser(event.getData()));
    }

    @Override
    @KafkaListener(topics = "tasks-completed")
    public void onTaskCompleted(TaskCompleted event) {
        receive(event, "task/TaskCompleted/1.json", o -> taskService.completeTask(event.getData()));
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
