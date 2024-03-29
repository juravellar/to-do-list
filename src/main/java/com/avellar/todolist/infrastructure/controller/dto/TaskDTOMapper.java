package com.avellar.todolist.infrastructure.controller.dto;

import com.avellar.todolist.domain.entity.TaskPort;

import java.time.LocalDateTime;

public class TaskDTOMapper {

  public TaskResponse toResponse(TaskPort taskPort) {
    return new TaskResponse(taskPort.id(), taskPort.name(), taskPort.description(), taskPort.prioritized(),
        taskPort.realized(), taskPort.createdAt(), taskPort.updatedAt(), taskPort.activityOrder());
  }

  public TaskPort totaskPort(TaskRequest request) {
    return new TaskPort(null, request.name(), request.description(), request.prioritized(),
            request.realized(), LocalDateTime.now(), null, null);
  }

}
