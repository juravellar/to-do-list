package com.avellar.todolist.infrastructure.controller;

import jdk.jshell.Snippet;

public record CreateTaskResponse(
        String name, String description,
        Boolean realized, Boolean prioritized
) {

    public Snippet builder(String name, String description, Boolean realized, Boolean prioritized) {
        this.name = name;
        this.description = description;
        this.realized = realized;
        this.prioritized = prioritized;
    }

}