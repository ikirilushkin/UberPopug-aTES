package com.kirilushkin.aa6.task.model.event;

import com.kirilushkin.aa6.task.model.event.TaskCompleted.TaskCompletedEventData;
import java.util.UUID;
import lombok.Getter;

public class TaskCompleted extends BaseEvent<TaskCompletedEventData> {

    @Getter
    public static class TaskCompletedEventData {
        private final String publicId;
        private final String assignee;

        public TaskCompletedEventData(UUID publicId, UUID assignee) {
            this.publicId = publicId.toString();
            this.assignee = assignee.toString();
        }
    }
}
