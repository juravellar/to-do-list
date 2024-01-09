package com.avelar.todolist.application.usecases;

import com.avelar.todolist.application.gateways.TaskGateway;
import com.avelar.todolist.domain.entity.TaskPort;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ListTaskInteractor {

  private final TaskGateway taskGateway;

  public TaskPort getById(Long id) {
    return taskGateway.getById(id);
  }

  public List<TaskPort> getByName(String name) {
    return taskGateway.getByName(name);
  }
}
