package com.avellar.todolist.application.usecases;

import com.avellar.todolist.application.gateways.TaskGateway;
import com.avellar.todolist.domain.entity.TaskPort;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
public class ListTaskInteractor {

  private final TaskGateway taskGateway;

  public TaskPort getById(Long id) {
    return taskGateway.getById(id);
  }

  public List<TaskPort> getByName(String name) {
    return taskGateway.getByName(name);
  }

  public List<TaskPort> getTaskByPriority(Boolean prioritized, LocalDateTime createdAt) {
    return taskGateway.getTaskByPriority(prioritized, createdAt);
  }
}
