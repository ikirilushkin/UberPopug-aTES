package com.kirilushkin.aa6.audit.service;

import com.kirilushkin.aa6.audit.model.entity.Task;
import com.kirilushkin.aa6.audit.model.event.TaskPriceCalculated.TaskPriceCalculatedEventData;
import com.kirilushkin.aa6.audit.repository.TaskRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskAggregateService taskAggregateService;

    @Override
    @Transactional
    public void saveTask(TaskPriceCalculatedEventData data) {
        UUID publicId = UUID.fromString(data.publicId());
        Optional<Task> optionalTask = taskRepository.findByPublicId(publicId);
        if (optionalTask.isEmpty()) {
            Task task = new Task(publicId);
            task.setDescription(data.description());
            task.setWithdraw(data.withdraw());
            task.setRefill(data.refill());
            task = taskRepository.save(task);
            taskAggregateService.calculateAggregate(task);
        }
    }
}
