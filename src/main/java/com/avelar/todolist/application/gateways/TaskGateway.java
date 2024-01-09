package com.avelar.todolist.application.gateways;

import com.avelar.todolist.domain.entity.TaskPort;
import java.util.List;

public interface TaskGateway {

  TaskPort createTask(TaskPort taskPort);

  TaskPort editTask(Long id, TaskPort taskPort);

  List<TaskPort> getByName(String name);

  TaskPort getById(Long id);
}
