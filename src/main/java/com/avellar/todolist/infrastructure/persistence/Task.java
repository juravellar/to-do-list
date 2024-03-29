package com.avellar.todolist.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("TASK")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Task {

  @Setter
  @Getter
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "name")
  private String name;
  @Column(name = "description")
  private String description;
  @Column(name = "prioritized")
  private Boolean prioritized;
  @Column(name = "realized")
  private Boolean realized;
  @Column(name = "created_at")
  private LocalDateTime createdAt;
  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updatedAt;
  @Column(name = "activity_order", nullable = false)
  private Long activityOrder;

}
