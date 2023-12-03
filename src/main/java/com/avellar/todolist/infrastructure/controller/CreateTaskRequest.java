package com.avellar.todolist.infrastructure.controller;

import jakarta.validation.constraints.NotBlank;

import java.time.DateTimeException;

public record CreateTaskRequest(Long id,
                                @NotBlank String name, @NotBlank String description,
                                Boolean realized, Boolean prioritized, DateTimeException createdAt,
                                DateTimeException updatedAt) {

}