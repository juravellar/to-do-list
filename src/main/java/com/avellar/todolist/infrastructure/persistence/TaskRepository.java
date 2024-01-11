package com.avellar.todolist.infrastructure.persistence;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task, Long> {

  Optional<Task> findByNameContaining(String name);

}
