package com.avellar.todolist.infrastructure.persistence;

import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface TaskRepository extends ReactiveCrudRepository<Task, Long>,
        ReactiveQueryByExampleExecutor<Task> {
    <S extends com.avellar.todolist.domain.entity.Task> Mono<S> save(S task);
}
