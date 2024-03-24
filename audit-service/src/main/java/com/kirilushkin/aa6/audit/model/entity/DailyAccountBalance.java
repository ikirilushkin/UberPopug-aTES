package com.kirilushkin.aa6.audit.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class DailyAccountBalance {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "account")
    private Account account;

    private LocalDate date;

    private Double balance = 0.0;

    private Boolean paid;

    public DailyAccountBalance() {
    }

    public DailyAccountBalance(Account account, Double balance) {
        this.account = account;
        this.balance = balance;
    }

    public synchronized void increase(Double amount) {
        this.balance += amount;
    }

    public synchronized void decrease(Double amount) {
        this.balance -= amount;
    }
}
