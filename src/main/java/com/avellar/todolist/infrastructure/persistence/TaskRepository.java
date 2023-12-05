package com.avellar.todolist.infrastructure.persistence;

import com.avellar.todolist.domain.entity.Task;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface TaskRepository extends ReactiveCrudRepository<TaskEntity, Long>,
        ReactiveQueryByExampleExecutor<TaskEntity> {
    <S extends Task> Mono<S> save(S task);
}
