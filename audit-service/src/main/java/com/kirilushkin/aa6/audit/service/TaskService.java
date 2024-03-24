package com.kirilushkin.aa6.audit.service;

import com.kirilushkin.aa6.audit.model.event.TaskPriceCalculated.TaskPriceCalculatedEventData;

public interface TaskService {

    void saveTask(TaskPriceCalculatedEventData data);
}
