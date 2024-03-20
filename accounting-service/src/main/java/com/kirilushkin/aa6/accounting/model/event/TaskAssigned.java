package com.kirilushkin.aa6.accounting.model.event;

import com.kirilushkin.aa6.accounting.model.event.TaskAssigned.TaskAssignedEventData;
import java.util.UUID;
import lombok.Getter;

public class TaskAssigned extends BaseEvent<TaskAssignedEventData> {

    @Getter
    public static class TaskAssignedEventData {
        private final String publicId;
        private final String assignee;

        public TaskAssignedEventData(UUID publicId, UUID assignee) {
            this.publicId = publicId.toString();
            this.assignee = assignee.toString();
        }
    }
}
