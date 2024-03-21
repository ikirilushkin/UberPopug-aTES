package com.kirilushkin.aa6.audit.service;

import java.time.LocalDate;
import java.util.UUID;

public interface BalanceService {

    void decreaseBalance(UUID account, LocalDate date, Double amount);

    void increaseBalance(UUID account, LocalDate date, Double amount);

    void setPaid(UUID account, LocalDate date);
}
