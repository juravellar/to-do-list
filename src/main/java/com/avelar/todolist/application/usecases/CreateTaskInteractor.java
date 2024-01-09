package com.avelar.todolist.application.usecases;

import com.avelar.todolist.application.gateways.TaskGateway;
import com.avelar.todolist.domain.entity.TaskPort;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateTaskInteractor {

  private final TaskGateway taskGateway;

  public TaskPort createTask(TaskPort taskPort) {
    return taskGateway.createTask(taskPort);
  }
}
