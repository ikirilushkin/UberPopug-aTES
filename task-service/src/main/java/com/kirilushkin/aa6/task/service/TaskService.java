package com.kirilushkin.aa6.task.service;

import com.kirilushkin.aa6.task.model.dto.TaskDto;
import com.kirilushkin.aa6.task.model.dto.TaskRequest;
import java.util.List;

public interface TaskService {

    void create(TaskRequest request);

    void update(TaskDto taskDto);
}
