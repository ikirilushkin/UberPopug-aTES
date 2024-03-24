package com.kirilushkin.aa6.audit.repository;

import com.kirilushkin.aa6.audit.model.entity.Account;
import com.kirilushkin.aa6.audit.model.entity.DailyAccountBalance;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyAccountBalanceRepository extends JpaRepository<DailyAccountBalance, UUID> {

    Optional<DailyAccountBalance> findByAccountAndDate(Account account, LocalDate date);
}
