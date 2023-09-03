package com.avellar.todolist.api;

import jakarta.validation.constraints.NotBlank;

public record TaskResponse(
        String name, String slug, String description,
        Boolean done, Boolean preference) {

}