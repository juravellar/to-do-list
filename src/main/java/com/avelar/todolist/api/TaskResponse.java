package com.avelar.todolist.api;

import java.time.LocalDateTime;

public record TaskResponse(String name, String description, Boolean prioritized, Boolean realized,
                           LocalDateTime createdAt, LocalDateTime updatedAt) {

}
