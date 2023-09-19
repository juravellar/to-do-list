package com.avelar.todolist.domain;

import com.avelar.todolist.api.TaskRequest;
import com.avelar.todolist.api.TaskResponse;
import org.springframework.util.StringUtils;

public class TaskMapper {
  public static Task updatePlaceFromDTO(TaskRequest taskRequest, Task task) {
    final String name = StringUtils.hasText(taskRequest.name()) ? taskRequest.name() : task.name();
    final String description = StringUtils.hasText(taskRequest.description()) ? taskRequest.description() : task.description();
    final Boolean prioritized = taskRequest.prioritized() ? taskRequest.prioritized() : task.prioritized();
    final Boolean realized = taskRequest.realized() ? taskRequest.realized() : task.realized();
    return new Task(task.id(), name, description, prioritized, realized, task.createdAt(), task.updatedAt());
  }

  public static TaskResponse toResponse(Task task) {
    return new TaskResponse(task.name(), task.description(),
        task.prioritized(), task.realized(), task.createdAt(), task.updatedAt());
  }
}
