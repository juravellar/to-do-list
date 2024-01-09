package com.avellar.todolist.infrastructure.gateways;

import com.avellar.todolist.application.gateways.TaskGateway;
import com.avellar.todolist.infrastructure.persistence.Task;
import com.avellar.todolist.infrastructure.persistence.TaskRepository;
import reactor.core.publisher.Mono;

import java.util.Objects;

public class TaskRepositoryGateway implements TaskGateway {
    private final TaskRepository taskRepository;
    private final TaskEntityMapper taskEntityMapper;

    public TaskRepositoryGateway(TaskRepository taskRepository, TaskEntityMapper taskEntityMapper) {
        this.taskRepository = taskRepository;
        this.taskEntityMapper = taskEntityMapper;
    }

    @Override
    public com.avellar.todolist.domain.entity.Task createTask(com.avellar.todolist.domain.entity.Task taskDomainObj) {
        Task task = taskEntityMapper.toEntity(taskDomainObj);
        Mono<Task> savedObj = taskRepository.save(task);
        return taskEntityMapper.toDomainObj(Objects.requireNonNull(savedObj.block()));
    }
}
