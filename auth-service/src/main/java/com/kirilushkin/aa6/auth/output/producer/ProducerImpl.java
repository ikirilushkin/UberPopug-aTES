package com.kirilushkin.aa6.auth.output.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kirilushkin.SchemaValidator;
import com.kirilushkin.aa6.auth.model.dto.UserDto;
import com.kirilushkin.aa6.auth.model.event.UserCreated;
import com.kirilushkin.aa6.auth.model.event.UserCreated.UserCreatedEventData;
import com.kirilushkin.aa6.auth.model.event.UserRoleChanged;
import com.kirilushkin.aa6.auth.model.event.UserRoleChanged.UserRoleChangedEventData;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ProducerImpl implements Producer {

    private static final String PRODUCER_NAME = "auth-service";

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final SchemaValidator schemaValidator;
    private final ObjectMapper objectMapper;

    public void sendUser(UserDto userDto) {
        UserCreatedEventData data = new UserCreatedEventData();
        data.setPublicId(userDto.getPublicId().toString());
        data.setFirstName(userDto.getFirstName());
        data.setLastName(userDto.getLastName());
        data.setEmail(userDto.getEmail());
        data.setRole(userDto.getRole());

        UserCreated event = new UserCreated();
        event.setEventId(UUID.randomUUID().toString());
        event.setEventVersion(1);
        event.setEventName("UserCreated");
        event.setEventTime(LocalDateTime.now());
        event.setEventProducer(PRODUCER_NAME);
        event.setData(data);

        try {
            var isValid = schemaValidator.validate(objectMapper.writeValueAsString(event),
                                                   "auth/UserCreated/1.json");
            if (isValid) {
                kafkaTemplate.send("users-stream", event);
                log.info("users-stream is sent");
            } else {
                throw new IllegalArgumentException("Schema is invalid");
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void sendUserRoleChanged(UserDto userDto) {
        UserRoleChangedEventData data = new UserRoleChangedEventData();
        data.setPublicId(userDto.getPublicId().toString());
        data.setRole(userDto.getRole());

        UserRoleChanged event = new UserRoleChanged();
        event.setEventId(UUID.randomUUID().toString());
        event.setEventVersion(1);
        event.setEventName("UserRoleChanged");
        event.setEventTime(LocalDateTime.now());
        event.setEventProducer(PRODUCER_NAME);
        event.setData(data);

        try {
            var isValid = schemaValidator.validate(objectMapper.writeValueAsString(event),
                                                   "auth/UserRoleChanged/1.json");
            if (isValid) {
                kafkaTemplate.send("users-role-changed", event);
                log.info("users-role-changed is sent");
            } else {
                throw new IllegalArgumentException("Schema is invalid");
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
