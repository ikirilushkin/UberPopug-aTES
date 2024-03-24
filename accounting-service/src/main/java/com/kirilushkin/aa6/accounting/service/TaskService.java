package com.kirilushkin.aa6.accounting.service;

import com.kirilushkin.aa6.accounting.model.event.TaskAdded.TaskAddedEventData;
import com.kirilushkin.aa6.accounting.model.event.TaskAssigned.TaskAssignedEventData;
import com.kirilushkin.aa6.accounting.model.event.TaskCompleted.TaskCompletedEventData;
import com.kirilushkin.aa6.accounting.model.event.TaskCreated.TaskCreatedEventData;

public interface TaskService {

    void createTask(TaskCreatedEventData event);

    void addTaskToUser(TaskAddedEventData event);

    void assignTaskToUser(TaskAssignedEventData event);

    void completeTask(TaskCompletedEventData event);
}
