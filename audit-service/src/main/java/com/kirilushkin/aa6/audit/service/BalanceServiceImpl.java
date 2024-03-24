package com.kirilushkin.aa6.audit.service;

import com.kirilushkin.aa6.audit.model.entity.Account;
import com.kirilushkin.aa6.audit.model.entity.DailyAccountBalance;
import com.kirilushkin.aa6.audit.repository.AccountRepository;
import com.kirilushkin.aa6.audit.repository.DailyAccountBalanceRepository;
import java.time.LocalDate;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class BalanceServiceImpl implements BalanceService {

    private final AccountRepository accountRepository;
    private final DailyAccountBalanceRepository dailyAccountBalanceRepository;

    @Override
    @Transactional
    public void decreaseBalance(UUID accountId, LocalDate date, Double amount) {
        Account account = accountRepository.findByPublicId(accountId).get();
        DailyAccountBalance dailyAccountBalance = dailyAccountBalanceRepository.findByAccountAndDate(account, date)
              .orElse(new DailyAccountBalance(account, 0.0));
        dailyAccountBalance.decrease(amount);
        dailyAccountBalanceRepository.save(dailyAccountBalance);

    }

    @Override
    @Transactional
    public void increaseBalance(UUID accountId, LocalDate date, Double amount) {
        Account account = accountRepository.findByPublicId(accountId).get();
        DailyAccountBalance dailyAccountBalance = dailyAccountBalanceRepository.findByAccountAndDate(account, date)
              .orElse(new DailyAccountBalance(account, 0.0));
        dailyAccountBalance.increase(amount);
        dailyAccountBalanceRepository.save(dailyAccountBalance);
    }

    @Override
    @Transactional
    public void setPaid(UUID accountId, LocalDate date) {
        Account account = accountRepository.findByPublicId(accountId).get();
        DailyAccountBalance dailyAccountBalance = dailyAccountBalanceRepository.findByAccountAndDate(account, date)
              .orElse(new DailyAccountBalance(account, 0.0));
        dailyAccountBalance.setPaid(true);
        dailyAccountBalanceRepository.save(dailyAccountBalance);
    }
}
