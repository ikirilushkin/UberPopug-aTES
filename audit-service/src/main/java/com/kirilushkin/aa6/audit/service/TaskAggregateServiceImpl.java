package com.kirilushkin.aa6.audit.service;

import com.kirilushkin.aa6.audit.model.entity.Task;
import com.kirilushkin.aa6.audit.model.entity.TaskPriceAggregate;
import com.kirilushkin.aa6.audit.model.entity.TaskPriceAggregate.TaskPriceAggregateType;
import com.kirilushkin.aa6.audit.repository.TaskPriceAggregateRepository;
import java.time.LocalDate;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskAggregateServiceImpl implements TaskAggregateService {

    private final TaskPriceAggregateRepository taskPriceAggregateRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void calculateAggregate(Task task) {
//        TODO: user date from task
        LocalDate date = LocalDate.now();
        Optional<TaskPriceAggregate> optionalDailyAggregate = taskPriceAggregateRepository.findByTypeAndDate(
              TaskPriceAggregateType.DAILY,
              date);
        TaskPriceAggregate aggregate;
        if (optionalDailyAggregate.isEmpty()) {
            aggregate = new TaskPriceAggregate(task.getRefill(), date, TaskPriceAggregateType.DAILY);
        } else {
            aggregate = optionalDailyAggregate.get();
            Double price = aggregate.getPrice();
            Double newPrice = (price + task.getRefill()) / 2;
            aggregate.setPrice(newPrice);
        }
        taskPriceAggregateRepository.save(aggregate);
    }
}
