package com.kirilushkin.aa6.accounting.input;

import com.kirilushkin.aa6.accounting.model.event.TaskAdded;
import com.kirilushkin.aa6.accounting.model.event.TaskCreated;
import com.kirilushkin.aa6.accounting.model.event.UserCreated;

public interface EventConsumer {

    void onUserCreated(UserCreated event);

    void onTaskCreated(TaskCreated event);

    void onTaskAdded(TaskAdded event);
}
