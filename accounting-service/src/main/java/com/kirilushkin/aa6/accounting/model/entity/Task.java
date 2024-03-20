package com.kirilushkin.aa6.accounting.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tasks")
@Getter
@Setter
public class Task {

    @Id
    private UUID id;

    @Column(name = "public_id")
    private UUID publicId;

    private String description;

    /*
    The amount taken from an assignee
     */
    private Double withdraw;

    /*
    The amount added to an assignee
     */
    private Double refill;
}
