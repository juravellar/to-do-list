package com.avellar.todolist.classes;

import com.avellar.todolist.infrastructure.persistence.Task;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

public class QueryBuilder {
    private QueryBuilder() {
    }

    public static Example<Task> makeQuery(Task task) {
        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll().withIgnoreCase().withIgnoreNullValues();
        return Example.of(task, exampleMatcher);
    }
}