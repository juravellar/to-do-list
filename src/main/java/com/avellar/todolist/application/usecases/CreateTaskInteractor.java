package com.avellar.todolist.application.usecases;

import com.avellar.todolist.application.gateways.TaskGateway;
import com.avellar.todolist.classes.QueryBuilder;
import com.avellar.todolist.infrastructure.persistence.Task;
import com.avellar.todolist.infrastructure.persistence.TaskRepository;
import com.avellar.todolist.classes.TaskMapper;
import com.avellar.todolist.infrastructure.controller.CreateTaskRequest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class CreateTaskInteractor {
    private final TaskGateway taskGateway;
    private final TaskRepository taskRepository;

    public CreateTaskInteractor(TaskGateway taskGateway, TaskRepository taskRepository) {
        this.taskGateway = taskGateway;
        this.taskRepository = taskRepository;
    }

    public com.avellar.todolist.domain.entity.Task createTask(com.avellar.todolist.domain.entity.Task task){
        return taskGateway.createTask(task);
    }

    public Mono<Task> create (CreateTaskRequest taskRequest) {
        var task = new Task(
                taskRequest.id(), taskRequest.name(), taskRequest.description(),taskRequest.prioritized(), taskRequest.realized());

        return taskRepository.save(task);
    }

    public Mono<com.avellar.todolist.domain.entity.Task> edit(Long id, CreateTaskRequest taskRequest) {
        return taskRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found")))
                .map(task -> TaskMapper.updateTaskFromDTO(taskRequest, task))
                .flatMap(taskRepository::save);
    }

    public Mono<Task> get(Long id, CreateTaskRequest request) {
        return taskRepository.findById(id);
    }

    public Flux<Task> list(String name) {
            var task = new Task(null, name, null, null, null);
            Example<Task> query = QueryBuilder.makeQuery(task);
        return taskRepository.findAll(query, Sort.by("name").ascending());
    }

    }

