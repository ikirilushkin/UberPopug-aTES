package com.kirilushkin.aa6.audit.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "daily_balances")
@Getter
@Setter
public class TaskPriceAggregate {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    private Double price;

    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private TaskPriceAggregateType type;

    public enum TaskPriceAggregateType {
        DAILY, WEEKLY, MONTHLY
    }

    public TaskPriceAggregate() {
    }

    public TaskPriceAggregate(Double price, LocalDate date, TaskPriceAggregateType type) {
        this.price = price;
        this.date = date;
        this.type = type;
    }
}
