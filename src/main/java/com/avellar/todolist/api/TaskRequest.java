package com.avellar.todolist.api;

import jakarta.validation.constraints.NotBlank;

public record TaskRequest(
        @NotBlank String name, @NotBlank String description,
        Boolean realized, Boolean prioritized) {

}