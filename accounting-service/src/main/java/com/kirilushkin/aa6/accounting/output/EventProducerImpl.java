package com.kirilushkin.aa6.accounting.output;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kirilushkin.SchemaValidator;
import com.kirilushkin.aa6.accounting.model.entity.Payment;
import com.kirilushkin.aa6.accounting.model.entity.Task;
import com.kirilushkin.aa6.accounting.model.entity.Transaction;
import com.kirilushkin.aa6.accounting.model.event.BaseEvent;
import com.kirilushkin.aa6.accounting.model.event.PaymentCompleted;
import com.kirilushkin.aa6.accounting.model.event.PaymentCompleted.PaymentCompletedEventData;
import com.kirilushkin.aa6.accounting.model.event.TaskPriceCalculated;
import com.kirilushkin.aa6.accounting.model.event.TaskPriceCalculated.TaskPriceCalculatedEventData;
import com.kirilushkin.aa6.accounting.model.event.TransactionApplied;
import com.kirilushkin.aa6.accounting.model.event.TransactionApplied.TransactionAppliedEventData;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class EventProducerImpl implements EventProducer {

    private static final String PRODUCER = "accounting-service";

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final SchemaValidator schemaValidator;
    private final ObjectMapper objectMapper;

    @Override
    public void sendTaskCalculated(Task task) {
        TaskPriceCalculated event = new TaskPriceCalculated();
        event.setEventId(UUID.randomUUID().toString());
        event.setEventName("TransactionApplied");
        event.setEventVersion(1);
        event.setEventTime(LocalDateTime.now());
        event.setEventProducer(PRODUCER);
        event.setData(new TaskPriceCalculatedEventData(task.getPublicId().toString(),
                                                       task.getDescription(),
                                                       task.getWithdraw(),
                                                       task.getRefill())
        );

        send(event, "task/TaskPriceCalculated/1.json", "transactions-applied");
    }

    @Override
    public void sendTransactionApplied(Transaction transaction) {
        TransactionApplied event = new TransactionApplied();
        event.setEventId(UUID.randomUUID().toString());
        event.setEventName("TransactionApplied");
        event.setEventVersion(1);
        event.setEventTime(LocalDateTime.now());
        event.setEventProducer(PRODUCER);
        event.setData(new TransactionAppliedEventData(transaction.getPublicId().toString(),
                                                      transaction.getFrom() != null
                                                      ? transaction.getFrom().getPublicId().toString()
                                                      : null,
                                                      transaction.getTo() != null
                                                      ? transaction.getTo().getPublicId().toString()
                                                      : null,
                                                      transaction.getAmount(),
                                                      transaction.getType())
        );

        send(event, "task/TransactionApplied/1.json", "transactions-applied");
    }

    @Override
    public void sendPaymentCompleted(Payment payment) {
        PaymentCompleted event = new PaymentCompleted();
        event.setEventId(UUID.randomUUID().toString());
        event.setEventName("PaymentCompleted");
        event.setEventVersion(1);
        event.setEventTime(LocalDateTime.now());
        event.setEventProducer(PRODUCER);
        event.setData(new PaymentCompletedEventData(payment.getPublicId().toString(),
                                                    payment.getAccount().getPublicId().toString(),
                                                    payment.getAmount(),
                                                    payment.getPaymentFor())
        );

        send(event, "task/PaymentCompleted/1.json", "payments-completed");
    }

    private <T> void send(BaseEvent<T> event, String schema, String topic) {
        try {
            var isValid = schemaValidator.validate(objectMapper.writeValueAsString(event), schema);
            if (isValid) {
                kafkaTemplate.send(topic, event);
                log.info(String.format("Event is sent to the topic %s", topic));
            } else {
                throw new IllegalArgumentException(String.format("Schema %s is invalid", schema));
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
