package com.kirilushkin.aa6.audit.service;

import com.kirilushkin.aa6.audit.model.event.PaymentCompleted.PaymentCompletedEventData;
import com.kirilushkin.aa6.audit.model.event.TransactionApplied.TransactionAppliedEventData;

public interface TransactionService {

    void saveTransaction(TransactionAppliedEventData data);

    void savePayment(PaymentCompletedEventData data);
}
