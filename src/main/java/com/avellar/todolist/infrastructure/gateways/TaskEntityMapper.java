package com.avellar.todolist.infrastructure.gateways;


import com.avellar.todolist.domain.entity.TaskPort;
import com.avellar.todolist.infrastructure.controller.dto.TaskDTOMapper;
import com.avellar.todolist.infrastructure.controller.dto.TaskRequest;
import com.avellar.todolist.infrastructure.persistence.Task;
import java.time.LocalDateTime;
import java.util.Objects;

public class TaskEntityMapper {

  //private static final int DEFAULT_ACTIVITY_ORDER = 1;

  Task toEntity(TaskPort taskPort) {
    return new Task(null, taskPort.name(), taskPort.description(), taskPort.prioritized(),
        taskPort.realized(), LocalDateTime.now(), LocalDateTime.now(), taskPort.activityOrder());
  }

  TaskPort toDomainObj(Task task, String message) {
    //Long activityOrder = task.getActivityOrder() != null ? task.getActivityOrder() : DEFAULT_ACTIVITY_ORDER;
    return new TaskPort(
            task.getId(),
            task.getName(),
            task.getDescription(),
            task.getPrioritized(),
            task.getRealized(),
            task.getCreatedAt(),
            task.getUpdatedAt(),
    //      activityOrder,
            task.getActivityOrder()
    );
  }

  public Task newTask(Task taskDb, TaskPort taskPort) {
    return Task.builder()
        .id(taskDb.getId())
        .name(Objects.nonNull(taskPort.name()) ? taskPort.name() : taskDb.getName())
        .description(Objects.nonNull(taskPort.description()) ? taskPort.description() : taskDb.getDescription())
        .prioritized(Objects.nonNull(taskPort.prioritized()) ? taskPort.prioritized() : taskDb.getPrioritized())
        .realized(Objects.nonNull(taskPort.realized()) ? taskPort.realized() : taskDb.getRealized())
        .createdAt(taskDb.getCreatedAt())
        .updatedAt(taskDb.getUpdatedAt())
        .activityOrder(Objects.nonNull(taskPort.activityOrder()) ? taskPort.activityOrder() : taskDb.getActivityOrder())

        .build();
  }
}
