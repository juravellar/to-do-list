package com.avelar.todolist.infrastructure.controller.dto;

import java.time.LocalDateTime;

public record TaskResponse(Long id, String name, String description, Boolean prioritized, Boolean realized,
                           LocalDateTime createdAt, LocalDateTime updatedAt) {

}
