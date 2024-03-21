package com.kirilushkin.aa6.audit.input.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kirilushkin.SchemaValidator;
import com.kirilushkin.aa6.audit.model.event.BaseEvent;
import com.kirilushkin.aa6.audit.model.event.PaymentCompleted;
import com.kirilushkin.aa6.audit.model.event.TransactionApplied;
import com.kirilushkin.aa6.audit.model.event.UserCreated;
import com.kirilushkin.aa6.audit.service.AccountService;
import com.kirilushkin.aa6.audit.service.TransactionService;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class EventConsumerImpl implements EventConsumer {

    private final AccountService accountService;
    private final TransactionService transactionService;
    private final SchemaValidator schemaValidator;
    private final ObjectMapper objectMapper;

    @Override
    @KafkaListener(topics = "users-stream")
    public void onUserSaved(UserCreated event) {
        receive(event, "auth/UserCreated/1.json", o -> accountService.saveAccount(event.getData()));
    }

    @Override
    @KafkaListener(topics = "transactions-applied")
    public void onTransactionApplied(TransactionApplied event) {
        receive(event, "task/TransactionApplied/1.json", o -> transactionService.saveTransaction(event.getData()));
    }

    @Override
    @KafkaListener(topics = "payments-completed")
    public void onPaymentCompleted(PaymentCompleted event) {
        receive(event, "task/PaymentCompleted/1.json", o -> transactionService.savePayment(event.getData()));
    }

    private <T extends BaseEvent> void receive(T event, String schema, Consumer<Object> consumer) {
        try {
            String msg = objectMapper.writeValueAsString(event);
            var isValid = schemaValidator.validate(msg, schema);
            if (isValid) {
                consumer.accept(event.getData());
            } else {
                throw new IllegalArgumentException(String.format("Schema %s is invalid for message %s", schema, msg));
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
