package com.kirilushkin.aa6.accounting.service;

import com.kirilushkin.aa6.accounting.model.entity.Account;
import com.kirilushkin.aa6.accounting.model.entity.Payment;
import com.kirilushkin.aa6.accounting.model.entity.Payment.PaymentStatus;
import com.kirilushkin.aa6.accounting.repository.PaymentRepository;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final TransactionService transactionService;

    @Override
    @Transactional
    public void proceedPayment(Account account) {
        Double balance = account.getBalance();
        Payment payment = new Payment();
        payment.setAccount(account);
        payment.setPaymentFor(LocalDate.now());
        payment.setAmount(balance);
        payment.setStatus(PaymentStatus.ACTIVE);
        payment = paymentRepository.save(payment);
        transactionService.proceedPaymentFor(account.getPublicId(), balance);
        payment.setStatus(PaymentStatus.COMPLETED);
        paymentRepository.save(payment);
    }
}
