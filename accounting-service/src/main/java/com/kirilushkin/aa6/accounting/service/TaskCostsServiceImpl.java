package com.kirilushkin.aa6.accounting.service;

import com.kirilushkin.aa6.accounting.model.TaskCosts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TaskCostsServiceImpl implements TaskCostsService {

    @Override
    public TaskCosts generate() {
        // todo
        return new TaskCosts(10.00, 9.00);
    }
}
