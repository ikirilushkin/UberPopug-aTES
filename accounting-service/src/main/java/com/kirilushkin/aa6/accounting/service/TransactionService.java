package com.kirilushkin.aa6.accounting.service;

import java.util.UUID;

public interface TransactionService {

    void withdrawFrom(UUID accountPublicId, Double amount);

    void enrollTo(UUID accountPublicId, Double amount);

    void proceedPaymentFor(UUID accountPublicId);
}
