package com.kirilushkin.aa6.accounting.repository;

import com.kirilushkin.aa6.accounting.model.entity.Payment;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {

}
