package com.kirilushkin.aa6.accounting.service;

import com.kirilushkin.aa6.accounting.model.event.UserCreated.UserCreatedEventData;

public interface AccountService {

    void saveAccount(UserCreatedEventData event);
}
