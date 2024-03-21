package com.kirilushkin.aa6.audit.model.event;

import com.kirilushkin.aa6.audit.model.event.PaymentCompleted.PaymentCompletedEventData;
import java.time.LocalDate;

public class PaymentCompleted extends BaseEvent<PaymentCompletedEventData> {

    public record PaymentCompletedEventData(String publicId, String account, Double amount, LocalDate date) {}
}
