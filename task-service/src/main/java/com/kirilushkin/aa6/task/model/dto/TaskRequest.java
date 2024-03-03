package com.kirilushkin.aa6.task.model.dto;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskRequest {

    private String description;
    private UUID assignee;
}
