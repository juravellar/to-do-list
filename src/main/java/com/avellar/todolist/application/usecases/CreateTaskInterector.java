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

    public Mono<TaskEntity> create(CreateTaskRequest taskRequest) {
        var task = new TaskEntity(
                taskRequest.name(), taskRequest.description(),taskRequest.prioritized(), taskRequest.realized());

        Mono<TaskEntity> save = taskRepository.save(task);
        return save;
    }

    public Mono<Task> edit(Long id, CreateTaskRequest taskRequest) {
        return taskRepository.findById(id)
                .map(task -> TaskMapper.updateTaskFromDTO(taskRequest, task))
                .flatMap(taskRepository::save);
    }

    public Mono<TaskEntity> get(Long id) {
        return taskRepository.findById(id);
    }

    public void list(String name) {
        var task = new TaskEntity(name, null, null, null);
        Example<TaskEntity> query = QueryBuilder.makeQuery(task);
        Flux<TaskEntity> name1 = taskRepository.findAll(query, Sort.by("name").ascending());
        return ;
    }
    }

