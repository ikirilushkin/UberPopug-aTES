package com.kirilushkin.aa6.task.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Task {

    @Id
    private UUID id;

    private String description;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "assignee", nullable = false)
    private User assignee;

    @ManyToOne
    @JoinColumn(name = "reporter", nullable = false)
    private User reporter;

}
