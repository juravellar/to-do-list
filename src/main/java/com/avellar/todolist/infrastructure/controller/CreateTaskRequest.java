package com.avellar.todolist.infrastructure.controller;

import jakarta.validation.constraints.NotBlank;

public record CreateTaskRequest(Long id,
                                @NotBlank String name, @NotBlank String description,
                                Boolean realized, Boolean prioritized
) {

    @Override
    public Long id() {
        return id;
    }

    @Override
    public @NotBlank String name() {
        return name;
    }

    @Override
    public @NotBlank String description() {
        return description;
    }

    @Override
    public Boolean realized() {
        return realized;
    }

    @Override
    public Boolean prioritized() {
        return prioritized;
    }

}