package com.avellar.todolist.classes;

import jakarta.validation.constraints.NotBlank;

public record TaskRequest(
        @NotBlank String name, @NotBlank String description,
        Boolean realized, Boolean prioritized) {

}