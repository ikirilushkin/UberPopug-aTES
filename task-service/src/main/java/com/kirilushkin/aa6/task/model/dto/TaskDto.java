package com.kirilushkin.aa6.task.model.dto;

import com.kirilushkin.aa6.task.model.entity.TaskStatus;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TaskDto {

    private UUID id;
    private TaskStatus status;
    private String description;
    private LocalDateTime createdAt;
    private UserDto assignee;
    private UserDto reporter;
}
