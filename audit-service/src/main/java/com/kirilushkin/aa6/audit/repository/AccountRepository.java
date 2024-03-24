package com.kirilushkin.aa6.audit.repository;

import com.kirilushkin.aa6.audit.model.entity.Account;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, UUID> {

    Optional<Account> findByPublicId(UUID publicId);
}
