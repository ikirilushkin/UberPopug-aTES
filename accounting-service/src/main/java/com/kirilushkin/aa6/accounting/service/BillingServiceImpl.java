package com.kirilushkin.aa6.accounting.service;

import com.kirilushkin.aa6.accounting.model.entity.Account;
import com.kirilushkin.aa6.accounting.model.entity.UserRole;
import com.kirilushkin.aa6.accounting.repository.AccountRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BillingServiceImpl implements BillingService {

    private final AccountRepository accountRepository;
    private final PaymentService paymentService;

    @Override
    public void proceed() {
        List<Account> accounts = accountRepository.findAll();
        accounts.stream()
              .filter(ac -> ac.getBalance() > 0)
              .forEach(paymentService::proceedPayment);
    }
}
