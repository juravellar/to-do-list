package com.avelar.todolist.infrastructure.gateways;


import com.avelar.todolist.domain.entity.TaskPort;
import com.avelar.todolist.infrastructure.persistence.Task;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import org.springframework.util.ObjectUtils;

public class TaskEntityMapper {

  Task toEntity(TaskPort taskPort) {
    return new Task(null, taskPort.name(), taskPort.description(), taskPort.prioritized(),
        taskPort.realized(),
        LocalDateTime.now(), LocalDateTime.now());
  }

  TaskPort toDomainObj(Task task) {
    return new TaskPort(task.getId(), task.getName(), task.getDescription(),
        task.getPrioritized(), task.getRealized(), task.getCreatedAt(),
        Objects.isNull(task.getUpdatedAt()) ? null : task.getUpdatedAt());
  }

  public Task newTask(Task taskDb, TaskPort taskPort) {
    return Task.builder()
        .id(taskDb.getId())
        .name(Objects.nonNull(taskPort.name()) ? taskPort.name() : taskDb.getName())
        .description(Objects.nonNull(taskPort.description()) ? taskPort.description() : taskDb.getDescription())
        .realized(Objects.nonNull(taskPort.realized()) ? taskPort.realized() : taskDb.getRealized())
        .prioritized(Objects.nonNull(taskPort.prioritized()) ? taskPort.prioritized() : taskDb.getPrioritized())
        .createdAt(taskDb.getCreatedAt())
        .updatedAt(LocalDateTime.now())
        .build();
  }
}
