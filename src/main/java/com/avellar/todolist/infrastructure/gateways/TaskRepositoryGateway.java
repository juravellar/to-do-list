package com.avellar.todolist.infrastructure.gateways;

import com.avellar.todolist.application.gateways.TaskGateway;
import com.avellar.todolist.domain.entity.TaskPort;
import com.avellar.todolist.infrastructure.persistence.Task;
import com.avellar.todolist.infrastructure.persistence.TaskRepository;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
public class TaskRepositoryGateway implements TaskGateway {

  private final TaskRepository taskRepository;
  private final TaskEntityMapper mapper;

  @Override
  public TaskPort createTask(TaskPort taskPort) {
    // Verificar se a task a ser cadastrada tem realized=true
    if (Boolean.TRUE.equals(taskPort.realized())) {
      throw new IllegalArgumentException("Não é permitido cadastrar uma task com realized=true");
    }

    // Verificar se já existe uma task com a mesma ordem de atividade
    Long activityOrder = taskPort.activityOrder();
    if (activityOrder != null && taskRepository.existsByActivityOrder(activityOrder)) {
      throw new IllegalArgumentException("Já existe uma task com a mesma ordem de atividade");
    }

    // Criar a nova task
    Task newTask = mapper.toEntity(taskPort);
    Task savedTask = taskRepository.save(newTask);

    // Buscar a task mais prioritária que a recém-cadastrada
    Task mostPrioritizedTask = taskRepository.getTaskByPriority();

    String message;
    if (mostPrioritizedTask != null) {
      message = String.format("Task cadastrada com sucesso. A task mais prioritária é: %s", mostPrioritizedTask.getName());
    } else {
      message = "Task cadastrada com sucesso, mas não há outras tasks prioritárias no momento.";
    }

    // Mapear e retornar a task recém-cadastrada
    return mapper.toDomainObj(savedTask, message);
  }


  @Override
  public TaskPort editTask(Long id, TaskPort taskPort) {
    var taskDb = taskRepository.findById(id);
    var taskEdit = mapper.newTask(taskDb.orElseThrow(), taskPort);
    var newTask = taskRepository.save(taskEdit);
    String message = "Task editada com sucesso!";
    return mapper.toDomainObj(newTask, message);
  }

  @Override
  public List<TaskPort> getByName(String name) {
    String message = "";
    if (Objects.isNull(name)) {
      var tasks = taskRepository.findAll();
      List<TaskPort> taskPorts = new ArrayList<>();
      tasks.forEach(task -> taskPorts.add(mapper.toDomainObj(task, message)));
      return taskPorts;
    }
    var task = taskRepository.findByNameContaining(name);
    if (Objects.nonNull(task)) {
      return List.of(mapper.toDomainObj(task.get(), message));
    }

    return Collections.emptyList();
  }

  @Override
  public TaskPort getById(Long id) {
    var task = taskRepository.findById(id);
    String message = "";
    return mapper.toDomainObj(task.orElseThrow(), message);
  }

  @Override
  public List<TaskPort> getTaskByPriority(Boolean prioritized, LocalDateTime createdAt) {
    List<Task> tasks;

    if (prioritized != null) {
      tasks = taskRepository.findAllByPrioritizedOrder(prioritized);
    } else {
      tasks = taskRepository.findAllByCreatedAtOrder(createdAt);
    }

    for (int i = 0; i < tasks.size(); i++) {
      Task task = tasks.get(i);
      task.setActivityOrder((long) (i + 1));
    }

    taskRepository.saveAll(tasks);

    String message = "";

    return tasks.stream()
            .map(task -> mapper.toDomainObj(task, message))
            .collect(Collectors.toList());
  }


  @Override
  public TaskPort completeTask(Long taskId) {
    Task task = taskRepository.findById(taskId)
            .orElseThrow(() -> new NoSuchElementException("Task não encontrada"));

    Task mostPrioritizedTask = taskRepository.getTaskByPriority();

    if (mostPrioritizedTask != null && task.getActivityOrder() < mostPrioritizedTask.getActivityOrder()) {
      throw new IllegalArgumentException("Não é permitido concluir uma task com prioridade menor do que a mais prioritária no banco");
    }

    task.setRealized(true);
    Task completedTask = taskRepository.save(task);

    return mapper.toDomainObj(completedTask, "Task concluída com sucesso");
  }
}

