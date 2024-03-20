package com.kirilushkin.aa6.accounting.model.event;

import com.kirilushkin.aa6.accounting.model.event.TaskCreated.TaskCreatedEventData;
import java.util.UUID;
import lombok.Getter;

public class TaskCreated extends BaseEvent<TaskCreatedEventData> {

    @Getter
    public static class TaskCreatedEventData {

        private final String publicId;
        private final String description;

        public TaskCreatedEventData(UUID publicId, String description) {
            this.publicId = publicId.toString();
            this.description = description;
        }
    }
}
