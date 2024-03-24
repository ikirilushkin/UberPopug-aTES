package com.kirilushkin.aa6.audit.service;

import com.kirilushkin.aa6.audit.model.entity.Task;

public interface TaskAggregateService {

    void calculateAggregate(Task task);
}
