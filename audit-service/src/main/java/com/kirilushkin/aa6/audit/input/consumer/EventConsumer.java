package com.kirilushkin.aa6.audit.input.consumer;

import com.kirilushkin.aa6.audit.model.event.PaymentCompleted;
import com.kirilushkin.aa6.audit.model.event.TaskPriceCalculated;
import com.kirilushkin.aa6.audit.model.event.TransactionApplied;
import com.kirilushkin.aa6.audit.model.event.UserCreated;

public interface EventConsumer {

    void onUserSaved(UserCreated event);

    void onTransactionApplied(TransactionApplied event);

    void onPaymentCompleted(PaymentCompleted event);

    void onTaskPriceCalculated(TaskPriceCalculated event);
}
