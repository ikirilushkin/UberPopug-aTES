package com.kirilushkin.aa6.task.input.controller;

import com.kirilushkin.aa6.task.model.dto.TaskDto;
import com.kirilushkin.aa6.task.model.dto.TaskRequest;
import com.kirilushkin.aa6.task.service.SecurityHelper;
import com.kirilushkin.aa6.task.service.TaskService;
import com.kirilushkin.aa6.task.service.TaskShufflingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final SecurityHelper securityHelper;
    private final TaskService taskService;
    private final TaskShufflingService taskShufflingService;

    @PostMapping
    public void createTask(@RequestBody TaskRequest request) {
        taskService.create(request);
    }

    @PutMapping
    public void updateTask(@RequestBody TaskDto taskDto) {
        taskService.update(taskDto);
    }

    @PostMapping
    public void shuffleTasks() {
        taskShufflingService.shuffleTasks();
    }
}
