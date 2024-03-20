package com.kirilushkin.aa6.task.output;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kirilushkin.SchemaValidator;
import com.kirilushkin.aa6.task.model.dto.TaskDto;
import com.kirilushkin.aa6.task.model.event.BaseEvent;
import com.kirilushkin.aa6.task.model.event.TaskAdded;
import com.kirilushkin.aa6.task.model.event.TaskAdded.TaskAddedEventData;
import com.kirilushkin.aa6.task.model.event.TaskAssigned;
import com.kirilushkin.aa6.task.model.event.TaskAssigned.TaskAssignedEventData;
import com.kirilushkin.aa6.task.model.event.TaskCompleted;
import com.kirilushkin.aa6.task.model.event.TaskCompleted.TaskCompletedEventData;
import com.kirilushkin.aa6.task.model.event.TaskCreated;
import com.kirilushkin.aa6.task.model.event.TaskCreated.TaskCreatedEventData;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventProducerImpl implements EventProducer {

    private final static String PRODUCER = "task-service";

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final SchemaValidator schemaValidator;
    private final ObjectMapper objectMapper;

    @Override
    public void sendTask(TaskDto taskDto) {
        TaskCreated event = new TaskCreated();
        event.setEventId(UUID.randomUUID().toString());
        event.setEventName("TaskCreated");
        event.setEventVersion(1);
        event.setEventTime(LocalDateTime.now());
        event.setEventProducer(PRODUCER);
        event.setData(new TaskCreatedEventData(taskDto.getPublicId(), taskDto.getDescription()));

        send(event, "task/TaskCreated/1.json", "tasks-stream");
    }

    @Override
    public void sendTaskAdded(TaskDto taskDto) {
        TaskAdded event = new TaskAdded();
        event.setEventId(UUID.randomUUID().toString());
        event.setEventName("TaskAdded");
        event.setEventVersion(1);
        event.setEventTime(LocalDateTime.now());
        event.setEventProducer(PRODUCER);
        event.setData(new TaskAddedEventData(taskDto.getPublicId(),
                                             taskDto.getAssignee().getPublicId(),
                                             taskDto.getReporter().getPublicId()));

        kafkaTemplate.send("tasks-added", event);
        send(event, "task/TaskAdded/1.json", "tasks-added");
    }

    @Override
    public void sendTaskAssigned(TaskDto taskDto) {
        TaskAssigned event = new TaskAssigned();
        event.setEventId(UUID.randomUUID().toString());
        event.setEventName("TaskAssigned");
        event.setEventVersion(1);
        event.setEventTime(LocalDateTime.now());
        event.setEventProducer(PRODUCER);
        event.setData(new TaskAssignedEventData(taskDto.getPublicId(), taskDto.getAssignee().getPublicId()));

        send(event, "task/TaskAssigned/1.json", "tasks-assigned");
    }

    @Override
    public void sendTaskCompleted(TaskDto taskDto) {
        TaskCompleted event = new TaskCompleted();
        event.setEventId(UUID.randomUUID().toString());
        event.setEventTime(LocalDateTime.now());
        event.setEventName("TaskCompleted");
        event.setEventVersion(1);
        event.setEventProducer(PRODUCER);
        event.setData(new TaskCompletedEventData(taskDto.getPublicId(), taskDto.getAssignee().getPublicId()));

        send(event, "task/TaskCompleted/1.json", "tasks-completed");
    }

    private <T> void send(BaseEvent<T> event, String schema, String topic) {
        try {
            var isValid = schemaValidator.validate(objectMapper.writeValueAsString(event), schema);
            if (isValid) {
                kafkaTemplate.send(topic, event);
                log.info(String.format("Event is sent to the topic %s", topic));
            } else {
                throw new IllegalArgumentException(String.format("Schema %s is invalid", schema));
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
