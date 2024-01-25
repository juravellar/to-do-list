package com.avellar.todolist.infrastructure.gateways;

import com.avellar.todolist.application.gateways.TaskGateway;
import com.avellar.todolist.domain.entity.TaskPort;
import com.avellar.todolist.infrastructure.persistence.TaskRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TaskRepositoryGateway implements TaskGateway {

  private final TaskRepository taskRepository;
  private final TaskEntityMapper mapper;

  @Override
  public TaskPort createTask(TaskPort taskPort) {
    var task = mapper.toEntity(taskPort);
    var taskDb = taskRepository.save(task);
    return mapper.toDomainObj(taskDb);
  }

  @Override
  public TaskPort editTask(Long id, TaskPort taskPort) {
    var taskDb = taskRepository.findById(id);
    var taskEdit = mapper.newTask(taskDb.orElseThrow(), taskPort);
    var newTask = taskRepository.save(taskEdit);
    return mapper.toDomainObj(newTask);
  }

  @Override
  public List<TaskPort> getByName(String name) {
    if (Objects.isNull(name)) {
      var tasks = taskRepository.findAll();
      List<TaskPort> taskPorts = new ArrayList<>();
      tasks.forEach(task -> taskPorts.add(mapper.toDomainObj(task)));
      return taskPorts;
    }
    var task = taskRepository.findByNameContaining(name);
    return List.of(mapper.toDomainObj(task.orElseThrow()));
  }

  @Override
  public TaskPort getById(Long id) {
    var task = taskRepository.findById(id);
    return mapper.toDomainObj(task.orElseThrow());
  }
}
