package com.avellar.todolist.application.usecases;

import com.avellar.todolist.application.gateways.TaskGateway;
import com.avellar.todolist.domain.entity.TaskPort;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EditTaskInteractor {

  private final TaskGateway taskGateway;

  public TaskPort editTask(Long id, TaskPort taskPort) {
    return taskGateway.editTask(id, taskPort);
  }

}
