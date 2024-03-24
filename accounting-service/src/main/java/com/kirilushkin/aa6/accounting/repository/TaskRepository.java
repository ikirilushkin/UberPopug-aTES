package com.kirilushkin.aa6.accounting.repository;

import com.kirilushkin.aa6.accounting.model.entity.Task;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, UUID> {

    Optional<Task> findByPublicId(UUID publicId);
}
