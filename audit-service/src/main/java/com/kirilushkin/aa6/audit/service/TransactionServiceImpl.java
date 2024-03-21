package com.kirilushkin.aa6.audit.service;

import com.kirilushkin.aa6.audit.model.entity.Account;
import com.kirilushkin.aa6.audit.model.entity.Transaction.TransactionType;
import com.kirilushkin.aa6.audit.model.event.PaymentCompleted.PaymentCompletedEventData;
import com.kirilushkin.aa6.audit.model.event.TransactionApplied.TransactionAppliedEventData;
import com.kirilushkin.aa6.audit.repository.AccountRepository;
import java.time.LocalDate;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final BalanceService balanceService;

    @Override
    public void saveTransaction(TransactionAppliedEventData data) {
        TransactionType type = data.type();
        Double amount = data.amount();
        UUID accountPublicId = UUID.fromString(data.accountFrom());
//        TODO: use transaction's date
        LocalDate date = LocalDate.now();
        if (TransactionType.WITHDRAWAL.equals(type)) {
            balanceService.decreaseBalance(accountPublicId, date, amount);
        } else if (TransactionType.ENROLLMENT.equals(type)) {
            balanceService.increaseBalance(accountPublicId, date, amount);
        }
    }

    @Override
    public void savePayment(PaymentCompletedEventData data) {
        UUID accountPublicId = UUID.fromString(data.account());
        LocalDate date = data.date();
        balanceService.setPaid(accountPublicId, date);
    }
}
