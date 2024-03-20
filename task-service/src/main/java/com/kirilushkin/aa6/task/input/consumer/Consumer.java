package com.kirilushkin.aa6.task.input.consumer;

import com.kirilushkin.aa6.task.model.event.UserCreated;
import com.kirilushkin.aa6.task.model.event.UserRoleChanged;

public interface Consumer {

    void onUserCreated(UserCreated event);

    void onUserRoleChanged(UserRoleChanged event);
}
