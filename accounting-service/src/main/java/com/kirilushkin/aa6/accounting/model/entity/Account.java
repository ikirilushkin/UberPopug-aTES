package com.kirilushkin.aa6.accounting.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "accounts")
@Getter
@Setter
public class Account {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(name = "public_id")
    private UUID publicId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String email;

    private Double balance = 0.0;

    public Account() {
    }

    public Account(UUID publicId, Double balance) {
        this.publicId = publicId;
        this.balance = balance;
    }

    public synchronized Double getBalance() {
        return this.balance;
    }

    public synchronized void changeBalance(Double amount) {
        this.balance += amount;
    }
}
