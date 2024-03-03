package com.kirilushkin.aa6.task.mapper;

import com.kirilushkin.aa6.task.model.dto.TaskDto;
import com.kirilushkin.aa6.task.model.entity.Task;

public interface TaskMapper {

    TaskDto toTaskDto(Task task);
}
