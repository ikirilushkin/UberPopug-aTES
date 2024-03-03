package com.kirilushkin.aa6.task.output;

import com.kirilushkin.aa6.task.model.dto.TaskDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventProducerImpl implements EventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void sendTask(TaskDto taskDto) {
        kafkaTemplate.send("tasks-stream", taskDto);
    }

    @Override
    public void reassignTask(TaskDto taskDto) {
        kafkaTemplate.send("tasks-assineeChanged", taskDto);
    }

    @Override
    public void changeStatus(TaskDto taskDto) {
        kafkaTemplate.send("tasks-statusChanged", taskDto);
    }
}
