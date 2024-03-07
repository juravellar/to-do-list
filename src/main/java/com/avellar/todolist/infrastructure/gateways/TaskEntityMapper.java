package com.avellar.todolist.infrastructure.gateways;


import com.avellar.todolist.domain.entity.TaskPort;
import com.avellar.todolist.infrastructure.persistence.Task;
import java.time.LocalDateTime;
import java.util.Objects;

public class TaskEntityMapper {

  Task toEntity(TaskPort taskPort) {
    return new Task(null, taskPort.name(), taskPort.description(), taskPort.prioritized(),
        taskPort.realized(), LocalDateTime.now(), LocalDateTime.now(), taskPort.activityOrder());
  }

  TaskPort toDomainObj(Task task, String message) {
    return new TaskPort(task.getId(), task.getName(), task.getDescription(),
        task.getPrioritized(), task.getRealized(), task.getCreatedAt(),
        Objects.isNull(task.getUpdatedAt()) ? null : task.getUpdatedAt(),
        Objects.isNull(task.getActivityOrder()) ? null : task.getActivityOrder());
  }

  public Task newTask(Task taskDb, TaskPort taskPort) {
    return Task.builder()
        .id(taskDb.getId())
        .name(Objects.nonNull(taskPort.name()) ? taskPort.name() : taskDb.getName())
        .description(Objects.nonNull(taskPort.description()) ? taskPort.description() : taskDb.getDescription())
        .prioritized(Objects.nonNull(taskPort.prioritized()) ? taskPort.prioritized() : taskDb.getPrioritized())
        .realized(Objects.nonNull(taskPort.realized()) ? taskPort.realized() : taskDb.getRealized())
        .createdAt(taskDb.getCreatedAt())
        .updatedAt(LocalDateTime.now())
        .activityOrder(Objects.nonNull(taskPort.activityOrder()) ? taskPort.activityOrder() : taskDb.getActivityOrder())

        .build();
  }
}
