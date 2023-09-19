package com.avelar.todolist.api;

import jakarta.validation.constraints.NotBlank;

public record TaskRequest(
    @NotBlank String name, @NotBlank String description, Boolean prioritized, Boolean realized) {

}
