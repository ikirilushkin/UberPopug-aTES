package com.kirilushkin.aa6.accounting.service;

import com.kirilushkin.aa6.accounting.model.TaskCosts;
import com.kirilushkin.aa6.accounting.model.entity.Task;
import com.kirilushkin.aa6.accounting.model.event.TaskAdded.TaskAddedEventData;
import com.kirilushkin.aa6.accounting.model.event.TaskAssigned.TaskAssignedEventData;
import com.kirilushkin.aa6.accounting.model.event.TaskCompleted.TaskCompletedEventData;
import com.kirilushkin.aa6.accounting.model.event.TaskCreated.TaskCreatedEventData;
import com.kirilushkin.aa6.accounting.model.exception.NotFoundException;
import com.kirilushkin.aa6.accounting.repository.TaskRepository;
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
    public void createTask(TaskCreatedEventData event) {
        TaskCosts costs = taskCostsService.generate();
        Task task = new Task();
        task.setPublicId(UUID.fromString(event.getPublicId()));
        task.setDescription(event.getDescription());
        task.setWithdraw(costs.getWithdraw());
        task.setRefill(costs.getRefill());
        taskRepository.save(task);
    }

    @Override
    public void addTaskToUser(TaskAddedEventData event) {
        UUID accountId = UUID.fromString(event.getAssignee());
        Task task = taskRepository.findByPublicId(UUID.fromString(event.getPublicId()))
              .orElseThrow(() -> new NotFoundException("Task", event.getPublicId()));
        transactionService.withdrawFrom(accountId, task.getWithdraw());
    }

    @Override
    public void assignTaskToUser(TaskAssignedEventData event) {
        UUID accountId = UUID.fromString(event.getAssignee());
        Task task = taskRepository.findByPublicId(UUID.fromString(event.getPublicId()))
              .orElseThrow(() -> new NotFoundException("Task", event.getPublicId()));
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
