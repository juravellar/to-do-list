package com.avellar.todolist.domain;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;

public record Task(
        @Id Long id, @NotBlank String name, String slug,
        String description, @NotBlank Boolean done,
        @NotBlank Boolean preference) {

    public Task withSlug(String slug) {
        return new Task(id, name, slug, description, done, preference);
    }
}