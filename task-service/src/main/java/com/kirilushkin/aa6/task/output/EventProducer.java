package com.kirilushkin.aa6.task.output;

import com.kirilushkin.aa6.task.model.dto.TaskDto;

public interface EventProducer {

    void sendTask(TaskDto taskDto);

    void sendTaskAdded(TaskDto taskDto);

    void sendTaskAssigned(TaskDto taskDto);

    void sendTaskCompleted(TaskDto taskDto);
}
