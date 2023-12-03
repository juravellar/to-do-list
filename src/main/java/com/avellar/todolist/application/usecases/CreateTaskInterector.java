package com.avellar.todolist.application.usecases;

import com.avellar.todolist.application.gateways.TaskGateway;
import com.avellar.todolist.classes.QueryBuilder;
import com.avellar.todolist.infrastructure.persistence.TaskEntity;
import com.avellar.todolist.infrastructure.persistence.TaskRepository;
import com.avellar.todolist.classes.TaskMapper;
import com.avellar.todolist.infrastructure.controller.CreateTaskRequest;
import com.avellar.todolist.domain.entity.Task;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class CreateTaskInterector {
    private final TaskGateway taskGateway;
    private TaskRepository taskRepository;

    public CreateTaskInterector(TaskGateway taskGateway ) {
        this.taskGateway = taskGateway;
        this.taskRepository = taskRepository;
    }

    public Task createTask(Task task){
        return taskGateway.createTask(task);
    }

    public Mono<Task> create(CreateTaskRequest taskRequest) {
        var task = new Task(
                taskRequest.name(), taskRequest.description(),taskRequest.prioritized(), taskRequest.realized());
        return taskRepository.save(task);
    }

    public Mono<Task> edit(Long id, CreateTaskRequest taskRequest) {
        return taskRepository.findById(id)
                .map(task -> TaskMapper.updateTaskFromDTO(taskRequest, task))
                .flatMap(taskRepository::save);
    }

    public Mono<TaskEntity> get(Long id) {
        return taskRepository.findById(id);
    }

    public Flux<Task> list(String name) {
        var task = new Task(name, null, null, null);
        Example<Task> query = QueryBuilder.makeQuery(task);
        return taskRepository.findAll(query, Sort.by("name").ascending());
    }
    }

