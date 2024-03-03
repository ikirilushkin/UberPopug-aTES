package com.kirilushkin.aa6.task.repository;

import com.kirilushkin.aa6.task.model.entity.Task;
import com.kirilushkin.aa6.task.model.entity.TaskStatus;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, UUID> {

    List<Task> findAllByStatusIs(TaskStatus status);
}
