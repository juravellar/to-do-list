package com.avellar.todolist.domain;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

public record Task(
        @Id Long id, @NotBlank String name,
        @NotBlank String description,
        @NotBlank Boolean realized,
        @NotBlank Boolean prioritized,
        @CreatedDate LocalDateTime createdAt,
        @LastModifiedDate LocalDateTime updatedAt) {

}