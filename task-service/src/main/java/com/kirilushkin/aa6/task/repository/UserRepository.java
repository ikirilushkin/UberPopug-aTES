package com.kirilushkin.aa6.task.repository;

import com.kirilushkin.aa6.task.model.entity.User;
import com.kirilushkin.aa6.task.model.entity.UserRole;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {

    List<User> findAllByRoleIs(UserRole role);

    Optional<User> findByEmail(String email);
}
