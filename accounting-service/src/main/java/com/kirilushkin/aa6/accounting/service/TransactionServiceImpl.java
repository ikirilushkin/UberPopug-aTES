package com.kirilushkin.aa6.accounting.service;

import com.kirilushkin.aa6.accounting.model.entity.Account;
import com.kirilushkin.aa6.accounting.model.entity.Transaction;
import com.kirilushkin.aa6.accounting.model.entity.Transaction.TransactionStatus;
import com.kirilushkin.aa6.accounting.model.entity.Transaction.TransactionType;
import com.kirilushkin.aa6.accounting.model.exception.NotFoundException;
import com.kirilushkin.aa6.accounting.output.EventProducer;
import com.kirilushkin.aa6.accounting.repository.AccountRepository;
import com.kirilushkin.aa6.accounting.repository.TransactionRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final EventProducer eventProducer;

    @Override
    @Transactional
    public void withdrawFrom(UUID accountPublicId, Double amount) {
        Account accountFrom = accountRepository.findByPublicId(accountPublicId)
              .orElseThrow(() -> new NotFoundException("Account", accountPublicId));
        Transaction transaction = new Transaction();
        transaction.setFrom(accountFrom);
        transaction.setAmount(amount);
        transaction.setType(TransactionType.WITHDRAWAL);
        transaction.setPublicId(UUID.randomUUID());
        transaction.setStatus(TransactionStatus.COMPLETED);
        transactionRepository.save(transaction);
        accountFrom.changeBalance(-amount);
        accountRepository.save(accountFrom);
    }

    @Override
    @Transactional
    public void enrollTo(UUID accountPublicId, Double amount) {
        Account accountTo = accountRepository.findByPublicId(accountPublicId)
              .orElseThrow(() -> new NotFoundException("Account", accountPublicId));
        Transaction transaction = new Transaction();
        transaction.setTo(accountTo);
        transaction.setAmount(amount);
        transaction.setType(TransactionType.ENROLLMENT);
        transaction.setPublicId(UUID.randomUUID());
        transaction.setStatus(TransactionStatus.COMPLETED);
        transactionRepository.save(transaction);
        accountTo.changeBalance(amount);
        accountRepository.save(accountTo);
    }

    @Override
    public void proceedPaymentFor(UUID accountPublicId, Double amount) {
        Account account = accountRepository.findByPublicId(accountPublicId)
              .orElseThrow(() -> new NotFoundException("Account", accountPublicId));
        if (account.getBalance() > 0) {
            Transaction transaction = new Transaction();
            transaction.setFrom(account);
            transaction.setAmount(account.getBalance());
            transaction.setType(TransactionType.PAYMENT);
            transaction.setPublicId(UUID.randomUUID());
            transaction.setStatus(TransactionStatus.ACTIVE);
            transactionRepository.save(transaction);
            transaction.setStatus(TransactionStatus.COMPLETED);
            transactionRepository.save(transaction);
            account.setBalance(0.0);
            accountRepository.save(account);
        }
    }
}
