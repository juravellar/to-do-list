package com.avellar.todolist.classes;

import com.avellar.todolist.domain.entity.Task;
import com.avellar.todolist.infrastructure.persistence.TaskEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

public class QueryBuilder {
    private QueryBuilder() {
    }

    public static Example<TaskEntity> makeQuery(TaskEntity task) {
        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll().withIgnoreCase().withIgnoreNullValues();
        return Example.of(task, exampleMatcher);
    }
}