package com.kirilushkin.aa6.accounting.input;

import com.kirilushkin.aa6.accounting.model.event.TaskAdded;
import com.kirilushkin.aa6.accounting.model.event.TaskAssigned;
import com.kirilushkin.aa6.accounting.model.event.TaskCompleted;
import com.kirilushkin.aa6.accounting.model.event.TaskCreated;
import com.kirilushkin.aa6.accounting.model.event.UserCreated;

public interface EventConsumer {

    void onUserSaved(UserCreated event);

    void onTaskSaved(TaskCreated event);

    void onTaskAdded(TaskAdded event);

    void onTaskAssigned(TaskAssigned event);

    void onTaskCompleted(TaskCompleted event);
}
