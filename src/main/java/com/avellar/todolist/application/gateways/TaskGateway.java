package com.avellar.todolist.application.gateways;

import com.avellar.todolist.domain.entity.TaskPort;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskGateway {

  TaskPort createTask(TaskPort taskPort);

  TaskPort editTask(Long id, TaskPort taskPort);

  List<TaskPort> getByName(String name);

  TaskPort getById(Long id);

  List<TaskPort> getTaskByPriority(Boolean prioritized, LocalDateTime createdAt);

  TaskPort completeTask(Long id);
}
