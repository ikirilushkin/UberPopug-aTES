package com.kirilushkin.aa6.task.output;

import com.kirilushkin.aa6.task.model.dto.TaskDto;

public interface EventProducer {

    void sendTask(TaskDto taskDto);

    void reassignTask(TaskDto taskDto);

    void changeStatus(TaskDto taskDto);
}
