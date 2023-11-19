package com.avellar.todolist.infrastructure.controller;

import jakarta.validation.constraints.NotBlank;

public record CreateTaskRequest(
        @NotBlank String name, @NotBlank String description,
        Boolean realized, Boolean prioritized) {

}