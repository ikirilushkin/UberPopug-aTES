package com.kirilushkin.aa6.audit.repository;

import com.kirilushkin.aa6.audit.model.entity.TaskPriceAggregate;
import com.kirilushkin.aa6.audit.model.entity.TaskPriceAggregate.TaskPriceAggregateType;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskPriceAggregateRepository extends JpaRepository<TaskPriceAggregate, UUID> {

    Optional<TaskPriceAggregate> findByTypeAndDate(TaskPriceAggregateType type, LocalDate date);
}
