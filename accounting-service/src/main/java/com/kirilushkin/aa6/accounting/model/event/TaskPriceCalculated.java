package com.kirilushkin.aa6.accounting.model.event;

import com.kirilushkin.aa6.accounting.model.event.TaskPriceCalculated.TaskPriceCalculatedEventData;

public class TaskPriceCalculated extends BaseEvent<TaskPriceCalculatedEventData> {

    public record TaskPriceCalculatedEventData(String publicId, String description, Double withdraw, Double refill) {

    }
}
