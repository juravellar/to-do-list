package com.avellar.todolist.infrastructure.persistence;

import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface TaskRepository extends ReactiveCrudRepository<TaskEntity, Long>,
        ReactiveQueryByExampleExecutor<TaskEntity> {

}
