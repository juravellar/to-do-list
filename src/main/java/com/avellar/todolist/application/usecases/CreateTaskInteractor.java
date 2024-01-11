package com.avellar.todolist.application.usecases;

import com.avellar.todolist.application.gateways.TaskGateway;
import com.avellar.todolist.domain.entity.TaskPort;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateTaskInteractor {

  private final TaskGateway taskGateway;

  public TaskPort createTask(TaskPort taskPort) {
    return taskGateway.createTask(taskPort);
  }
}
