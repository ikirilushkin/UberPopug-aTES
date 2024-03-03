package com.kirilushkin.aa6.auth.repository;

import com.kirilushkin.aa6.auth.model.entity.User;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {

    List<User> findAllByDeletedIsFalse();
}
