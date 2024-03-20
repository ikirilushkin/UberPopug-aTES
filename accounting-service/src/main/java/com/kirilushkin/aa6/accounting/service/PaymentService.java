package com.kirilushkin.aa6.accounting.service;

import com.kirilushkin.aa6.accounting.model.entity.Account;

public interface PaymentService {

    void proceedPayment(Account account);
}
