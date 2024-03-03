package com.kirilushkin.aa6.task.service;

import com.kirilushkin.aa6.task.mapper.TaskMapper;
import com.kirilushkin.aa6.task.model.entity.Task;
import com.kirilushkin.aa6.task.model.entity.TaskStatus;
import com.kirilushkin.aa6.task.model.entity.User;
import com.kirilushkin.aa6.task.model.entity.UserRole;
import com.kirilushkin.aa6.task.repository.TaskRepository;
import com.kirilushkin.aa6.task.repository.UserRepository;
import java.util.List;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskShufflingServiceImpl implements TaskShufflingService {

    private final TaskService taskService;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;

    @Override
    public void shuffleTasks() {
        List<Task> activeTasks = taskRepository.findAllByStatusIs(TaskStatus.IN_PROGRESS);
        List<User> users = userRepository.findAllByRoleIs(UserRole.USER);
        activeTasks.forEach(task -> {
            User assignee = getRandomAssignee(users);
            task.setAssignee(assignee);
            taskService.update(taskMapper.toTaskDto(task));
        });
    }

    private User getRandomAssignee(List<User> users) {
        Random random = new Random();
        int idx = random.ints(0, users.size())
              .findFirst()
              .getAsInt();
        return users.get(idx);
    }
}
