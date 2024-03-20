package com.kirilushkin.aa6.task.mapper;

import com.kirilushkin.aa6.task.model.dto.TaskDto;
import com.kirilushkin.aa6.task.model.entity.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskMapperImpl implements TaskMapper {

    private final UserMapper userMapper;

    @Override
    public TaskDto toTaskDto(Task task) {
        return TaskDto.builder()
              .id(task.getId())
              .description(task.getDescription())
              .createdAt(task.getCreatedAt())
              .status(task.getStatus())
              .assignee(userMapper.toUserDto(task.getAssignee()))
              .reporter(userMapper.toUserDto(task.getReporter()))
              .publicId(task.getPublicId())
              .build();
    }
}
