package com.avellar.todolist.domain.entity;

import java.time.LocalDateTime;

public record TaskPort(Long id, String name, String description, Boolean prioritized, Boolean realized,
                       LocalDateTime createdAt, LocalDateTime updatedAt) {

}
