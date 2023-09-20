package com.avellar.todolist.api;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record TaskResponse(
        String name, String description,
        Boolean realized, Boolean prioritized,
        LocalDateTime createdAt, LocalDateTime updatedAp) {

}