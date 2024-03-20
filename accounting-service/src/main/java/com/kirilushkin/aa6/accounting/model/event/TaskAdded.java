package com.kirilushkin.aa6.accounting.model.event;

import com.kirilushkin.aa6.accounting.model.event.TaskAdded.TaskAddedEventData;
import java.util.UUID;
import lombok.Getter;

public class TaskAdded extends BaseEvent<TaskAddedEventData> {

    @Getter
    public static class TaskAddedEventData {
        private final String publicId;
        private final String assignee;
        private final String reporter;

        public TaskAddedEventData(UUID publicId, UUID assignee, UUID reporter) {
            this.publicId = publicId.toString();
            this.assignee = assignee.toString();
            this.reporter = reporter.toString();
        }
    }
}
