package com.avellar.todolist.infrastructure.controller;

public record CreateTaskResponse(
        String name, String description,
        Boolean realized, Boolean prioritized
) {
}