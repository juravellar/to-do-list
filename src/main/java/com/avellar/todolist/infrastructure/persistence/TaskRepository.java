package com.avellar.todolist.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TaskRepository extends CrudRepository<Task, Long> {

  Optional<Task> findByNameContaining(String name);

  List<Task> findAllByCreatedAt(LocalDateTime createdAt);

  boolean existsByActivityOrder(Long activityOrder);

  List<Task> findAllByPrioritized(Boolean prioritized);
}
