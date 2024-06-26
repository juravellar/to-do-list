package com.avellar.todolist.infrastructure.gateways;

import com.avellar.todolist.application.gateways.TaskGateway;
import com.avellar.todolist.domain.entity.TaskPort;
import com.avellar.todolist.infrastructure.persistence.Task;
import com.avellar.todolist.infrastructure.persistence.TaskRepository;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
public class TaskRepositoryGateway implements TaskGateway {

  private final TaskRepository taskRepository;
  private final TaskEntityMapper mapper;


    @Override
    public List<TaskPort> getTaskByPriority(Boolean prioritized, LocalDateTime createdAt) {
        List<Task> tasks;

        if (prioritized != null) {
            tasks = taskRepository.findAllByPrioritized(prioritized);
        } else {
            tasks = taskRepository.findAllByCreatedAt(createdAt);
        }

        return tasks.stream()
                .map(task -> mapper.toDomainObj(task, ""))
                .collect(Collectors.toList());
    }

  @Override
  public TaskPort createTask(@NotNull TaskPort taskPort, Boolean isPrioritized) {
      // Verificar se a task a ser cadastrada tem realized=true
      if (Boolean.TRUE.equals(taskPort.realized())) {
          throw new IllegalArgumentException("Não é permitido cadastrar uma task com realized=true");
      }

      // Criar a nova task
      Task newTask = mapper.toEntity(taskPort);
      newTask.setPrioritized(isPrioritized);
      Task savedTask = taskRepository.save(newTask);

      // Buscar a task mais prioritária que a recém-cadastrada
      String message = null;
      if (Boolean.TRUE.equals(isPrioritized)) {
          List<TaskPort> prioritizedTasks = getTaskByPriority(true, savedTask.getCreatedAt());

          if (!prioritizedTasks.isEmpty()) {
              TaskPort mostPrioritizedTask = prioritizedTasks.get(0);
              message = String.format("Task cadastrada com sucesso. A task mais prioritária é: %s", mostPrioritizedTask.name());
          } else {
              message = "Task cadastrada com sucesso, mas não há outras tasks prioritárias no momento.";
          }

          // Mapear e retornar a task recém-cadastrada
          return mapper.toDomainObj(savedTask, message);
      }

      return mapper.toDomainObj(savedTask, message);
  }
  @Override
  public TaskPort editTask(Long id, TaskPort taskPort) {
    var taskDb = taskRepository.findById(id);
    var taskEdit = mapper.newTask(taskDb.orElseThrow(), taskPort);

    if (Boolean.TRUE.equals(taskEdit.getPrioritized())) {
      throw new IllegalArgumentException("Não é permitido editar uma task prioritária");
    }

    var newTask = taskRepository.save(taskEdit);
    String message = "Task editada com sucesso!";
    return mapper.toDomainObj(newTask, message);
  }

  @Override
  public List<TaskPort> getByName(String name) {
    if (Objects.isNull(name)) {
      var tasks = taskRepository.findAll();
      List<TaskPort> taskPorts = new ArrayList<>();
      String message = "";
      tasks.forEach(task -> taskPorts.add(mapper.toDomainObj(task, message)));
      return taskPorts;
    }
    var tasks = taskRepository.findByNameContaining(name);
    return tasks.stream()
            .map(task -> mapper.toDomainObj(task, ""))
            .collect(Collectors.toList());
  }

  @Override
  public TaskPort getById(Long id) {
    var task = taskRepository.findById(id);
    String message ="";
    return mapper.toDomainObj(task.orElseThrow(), message);
  }

  @Override
  public TaskPort completeTask(Long id) {
    Task task = taskRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Task não encontrada"));

    if (Boolean.TRUE.equals(task.getPrioritized())) {
      throw new IllegalArgumentException("Não é permitido concluir uma task prioritária");
    }

    task.setRealized(true);
    Task completedTask = taskRepository.save(task);

    String message ="";
    return mapper.toDomainObj(completedTask, message);
  }
}

