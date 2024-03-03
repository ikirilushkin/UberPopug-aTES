package com.kirilushkin.aa6.task.service;

import com.kirilushkin.aa6.task.mapper.TaskMapper;
import com.kirilushkin.aa6.task.model.dto.TaskRequest;
import com.kirilushkin.aa6.task.model.entity.Task;
import com.kirilushkin.aa6.task.model.entity.TaskStatus;
import com.kirilushkin.aa6.task.model.dto.TaskDto;
import com.kirilushkin.aa6.task.model.entity.User;
import com.kirilushkin.aa6.task.model.exception.NotFoundException;
import com.kirilushkin.aa6.task.output.EventProducer;
import com.kirilushkin.aa6.task.repository.TaskRepository;
import com.kirilushkin.aa6.task.repository.UserRepository;
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
    private final UserRepository userRepository;
    private final EventProducer eventProducer;
    private final TaskMapper taskMapper;
    private final SecurityHelper securityHelper;

    @Override
    public void create(TaskRequest request) {
        User assignee = getAssignee(request.getAssignee());
        User reporter = securityHelper.getCurrentUser();
        Task task = new Task();
        task.setDescription(request.getDescription());
        task.setStatus(TaskStatus.IN_PROGRESS);
        task.setAssignee(assignee);
        task.setReporter(reporter);
        task = taskRepository.save(task);
        TaskDto taskDto = taskMapper.toTaskDto(task);
        eventProducer.sendTask(taskDto);
        eventProducer.changeStatus(taskDto);
    }

    @Override
    @Transactional
    public void update(TaskDto taskDto) {
        UUID id = taskDto.getId();
        TaskStatus status = taskDto.getStatus();
        User assignee = getAssignee(taskDto.getAssignee().getId());
        Task task = taskRepository.findById(id).orElseThrow(() -> new NotFoundException("Task", id.toString()));
        TaskStatus prevStatus = task.getStatus();
        User prevAssignee = task.getAssignee();
        task.setDescription(taskDto.getDescription());
        task.setStatus(status);
        task.setAssignee(assignee);
        task = taskRepository.save(task);
        TaskDto updated = taskMapper.toTaskDto(task);
        eventProducer.sendTask(updated);
        if (!status.equals(prevStatus) && TaskStatus.COMPLETED.equals(status)) {
            eventProducer.changeStatus(updated);
        } else if (!assignee.equals(prevAssignee)) {
            eventProducer.reassignTask(updated);
        }
    }

    private User getAssignee(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User", id));
    }
}
