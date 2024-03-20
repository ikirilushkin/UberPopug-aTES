package com.kirilushkin.aa6.task.model.event;

import com.kirilushkin.aa6.task.model.event.TaskCreated.TaskCreatedEventData;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

public class TaskCreated extends BaseEvent<TaskCreatedEventData> {

    @Getter
    public static class TaskCreatedEventData {

        private String publicId;
        private String description;

        public TaskCreatedEventData(UUID publicId, String description) {
            this.publicId = publicId.toString();
            this.description = description;
        }
    }
}
