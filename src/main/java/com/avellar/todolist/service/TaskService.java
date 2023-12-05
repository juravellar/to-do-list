package com.avellar.todolist.service;

import com.avellar.todolist.infrastructure.controller.CreateTaskRequest;
import com.avellar.todolist.classes.TaskMapper;
import com.avellar.todolist.classes.QueryBuilder;
import com.avellar.todolist.domain.entity.Task;
import com.avellar.todolist.infrastructure.persistence.TaskEntity;
import com.avellar.todolist.infrastructure.persistence.TaskRepository;
import com.github.slugify.Slugify;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class TaskService {
    private final TaskRepository taskRepository;
    private final Slugify slg;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
        this.slg = Slugify.builder().build();
    }

    public Mono<Task> create(CreateTaskRequest taskRequest) {
        var task = new Task(
                taskRequest.id(), taskRequest.name(), taskRequest.description(), taskRequest.prioritized(), taskRequest.r());
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
        var taskEntity = new TaskEntity(null, name, null, null, null);
        Example<TaskEntity> query = Example.of(taskEntity);
        return taskRepository.findAll(query, Sort.by("name").ascending());
    }
}
}
