package com.avelar.todolist.domain;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

public record Task(
  @Id Long id, @NotBlank String name,
  @NotBlank String description,
  @NotBlank Boolean prioritized,
  @NotBlank Boolean realized,
  @CreatedDate LocalDateTime createdAt,
  @LastModifiedDate LocalDateTime updatedAt) {

}
