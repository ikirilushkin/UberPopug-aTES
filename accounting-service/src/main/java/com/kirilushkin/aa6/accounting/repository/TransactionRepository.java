package com.kirilushkin.aa6.accounting.repository;

import com.kirilushkin.aa6.accounting.model.entity.Transaction;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

}
