package com.avellar.todolist.domain.entity;

public record Task (Long id, String name, String description, Boolean realized, Boolean prioritized) {

}
