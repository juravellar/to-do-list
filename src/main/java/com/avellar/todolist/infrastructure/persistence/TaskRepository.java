package com.avellar.todolist.infrastructure.persistence;

import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TaskRepository extends CrudRepository<Task, Long> {

  Optional<Task> findByNameContaining(String name);

  List<Task> findAllByCreatedAtOrder(LocalDateTime createdAt);

  List<Task> findAllByPrioritizedOrder(Boolean prioritized);

  boolean existsByActivityOrder(Long activityOrder);

  Task getTaskByPriority();
}
