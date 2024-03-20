package com.kirilushkin.aa6.accounting.service;

import com.kirilushkin.aa6.accounting.model.TaskCosts;
import com.kirilushkin.aa6.accounting.model.entity.Task;
import com.kirilushkin.aa6.accounting.model.event.TaskAdded.TaskAddedEventData;
import com.kirilushkin.aa6.accounting.model.event.TaskAssigned.TaskAssignedEventData;
import com.kirilushkin.aa6.accounting.model.event.TaskCompleted.TaskCompletedEventData;
import com.kirilushkin.aa6.accounting.model.event.TaskCreated.TaskCreatedEventData;
import com.kirilushkin.aa6.accounting.model.exception.NotFoundException;
import com.kirilushkin.aa6.accounting.repository.TaskRepository;
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

    private final TaskCostsService taskCostsService;
    private final TaskRepository taskRepository;
    private final TransactionService transactionService;

    @Override
    @Transactional
    public void createTask(TaskCreatedEventData data) {
        UUID publicId = UUID.fromString(data.getPublicId());
        Task task = taskRepository.findByPublicId(publicId).orElse(new Task(publicId));
        task.setDescription(data.getDescription());
        if (!task.arePricesGenerated()) {
            TaskCosts costs = taskCostsService.generate();
            task.setPrices(costs.getWithdraw(), costs.getRefill());
        }
        taskRepository.save(task);
    }

    @Override
    public void addTaskToUser(TaskAddedEventData data) {
        UUID accountId = UUID.fromString(data.getAssignee());
        UUID publicId = UUID.fromString(data.getPublicId());
        Optional<Task> taskOptional = taskRepository.findByPublicId(publicId);
        // todo
        while (taskOptional.isEmpty()) {
            log.info("Task {} is not received yet...waiting", publicId);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException("Error when adding a task");
            }
        }
        Task task = taskOptional.get();
        transactionService.withdrawFrom(accountId, task.getWithdraw());
    }

    @Override
    public void assignTaskToUser(TaskAssignedEventData data) {
        UUID accountId = UUID.fromString(data.getAssignee());
        UUID publicId = UUID.fromString(data.getPublicId());
        Task task = taskRepository.findByPublicId(publicId).orElseThrow(() -> new NotFoundException("Task", publicId));
        transactionService.withdrawFrom(accountId, task.getWithdraw());
    }

    @Override
    public void completeTask(TaskCompletedEventData event) {
        UUID accountId = UUID.fromString(event.getAssignee());
        Task task = taskRepository.findByPublicId(UUID.fromString(event.getPublicId()))
              .orElseThrow(() -> new NotFoundException("Task", event.getPublicId()));
        transactionService.enrollTo(accountId, task.getRefill());
    }
}
