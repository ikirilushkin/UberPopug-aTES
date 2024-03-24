package com.kirilushkin.aa6.audit.model.event;

import com.kirilushkin.aa6.audit.model.event.TaskPriceCalculated.TaskPriceCalculatedEventData;

public class TaskPriceCalculated extends BaseEvent<TaskPriceCalculatedEventData> {

    public record TaskPriceCalculatedEventData(String publicId, String description, Double withdraw, Double refill) {

    }
}
