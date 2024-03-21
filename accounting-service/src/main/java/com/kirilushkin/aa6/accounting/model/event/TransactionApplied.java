package com.kirilushkin.aa6.accounting.model.event;

import com.kirilushkin.aa6.accounting.model.entity.Transaction.TransactionType;
import com.kirilushkin.aa6.accounting.model.event.TransactionApplied.TransactionAppliedEventData;

public class TransactionApplied extends BaseEvent<TransactionAppliedEventData> {

    public record TransactionAppliedEventData(String publicId, String accountFrom, String accountTo, Double amount, TransactionType type) {}
}
