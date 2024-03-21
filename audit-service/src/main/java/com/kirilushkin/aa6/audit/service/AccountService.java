package com.kirilushkin.aa6.audit.service;

import com.kirilushkin.aa6.audit.model.event.UserCreated;
import com.kirilushkin.aa6.audit.model.event.UserCreated.UserCreatedEventData;

public interface AccountService {

    void saveAccount(UserCreatedEventData data);
}
