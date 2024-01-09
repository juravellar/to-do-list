package com.avellar.todolist.service;

import com.avellar.todolist.infrastructure.controller.CreateTaskRequest;
import com.avellar.todolist.classes.TaskMapper;
import com.avellar.todolist.infrastructure.persistence.Task;
import com.avellar.todolist.infrastructure.persistence.TaskRepository;
import com.github.slugify.Slugify;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class TaskService {
    private static TaskRepository taskRepository;
    private final Slugify slg;

    public TaskService(TaskRepository taskRepository) {
        TaskService.taskRepository = taskRepository;
        this.slg = Slugify.builder().build();
    }

    public Mono<com.avellar.todolist.domain.entity.Task> create(CreateTaskRequest taskRequest) {
        var task = new com.avellar.todolist.domain.entity.Task(
                taskRequest.id(), taskRequest.name(), taskRequest.description(), taskRequest.prioritized(), taskRequest.realized());
        return taskRepository.save(task);
    }

    public Mono<com.avellar.todolist.domain.entity.Task> edit(Long id, CreateTaskRequest taskRequest) {
        return taskRepository.findById(id)
                .map(task -> TaskMapper.updateTaskFromDTO(taskRequest, task))
                .flatMap(taskRepository::save);
    }

    public Mono<Task> get(Long id) {
        return taskRepository.findById(id);
    }

    public static Flux<Task> list(String name) {
        return Mono.justOrEmpty(name)
                .flatMapMany(filterName -> {
                    var taskEntity = new Task(null, name, null, null, null);
                    Example<Task> query = Example.of(taskEntity);
                    return taskRepository.findAll(query, Sort.by("name").ascending());
                });
    }
}

