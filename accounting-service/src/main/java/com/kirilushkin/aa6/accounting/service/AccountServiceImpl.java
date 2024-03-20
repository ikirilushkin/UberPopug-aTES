package com.kirilushkin.aa6.accounting.service;

import com.kirilushkin.aa6.accounting.model.entity.Account;
import com.kirilushkin.aa6.accounting.model.event.UserCreated.UserCreatedEventData;
import com.kirilushkin.aa6.accounting.repository.AccountRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private static final Double ZERO_BALANCE = 0.0;

    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public void saveAccount(UserCreatedEventData data) {
        UUID publicId = UUID.fromString(data.getPublicId());
        Account account = accountRepository.findByPublicId(publicId).orElse(new Account(publicId, ZERO_BALANCE));
        account.setFirstName(data.getFirstName());
        account.setLastName(data.getLastName());
        account.setEmail(data.getEmail());
        accountRepository.save(account);
    }
}
