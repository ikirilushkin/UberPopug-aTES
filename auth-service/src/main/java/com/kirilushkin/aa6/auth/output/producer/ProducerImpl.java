package com.kirilushkin.aa6.auth.output.producer;

import com.kirilushkin.aa6.auth.model.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ProducerImpl implements Producer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendUser(UserDto userDto) {
        kafkaTemplate.send("users-stream", userDto);
    }
}
