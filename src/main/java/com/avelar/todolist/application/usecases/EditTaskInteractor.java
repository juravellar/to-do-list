package com.avelar.todolist.application.usecases;

import com.avelar.todolist.application.gateways.TaskGateway;
import com.avelar.todolist.domain.entity.TaskPort;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EditTaskInteractor {

  private final TaskGateway taskGateway;

  public TaskPort editTask(Long id, TaskPort taskPort) {
    return taskGateway.editTask(id, taskPort);
  }


}
