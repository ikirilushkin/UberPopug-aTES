package com.kirilushkin.aa6.accounting.output;

import com.kirilushkin.aa6.accounting.model.entity.Payment;
import com.kirilushkin.aa6.accounting.model.entity.Task;
import com.kirilushkin.aa6.accounting.model.entity.Transaction;

public interface EventProducer {

    void sendTaskCalculated(Task task);

    void sendTransactionApplied(Transaction transaction);

    void sendPaymentCompleted(Payment event);
}
