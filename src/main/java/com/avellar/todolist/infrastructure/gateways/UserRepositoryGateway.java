package com.avellar.todolist.infrastructure.gateways;

import com.avellar.todolist.application.gateways.TaskGateway;
import com.avellar.todolist.domain.entity.Task;
import com.avellar.todolist.infrastructure.persistence.TaskEntity;
import com.avellar.todolist.infrastructure.persistence.TaskRepository;

public class UserRepositoryGateway implements TaskGateway {
    private final TaskRepository taskRepository;
    private final TaskEntityMapper taskEntityMapper;

    public UserRepositoryGateway(TaskRepository taskRepository, TaskEntityMapper taskEntityMapper) {
        this.taskRepository = taskRepository;
        this.taskEntityMapper = taskEntityMapper;
    }

    @Override
    public Task createTask(Task taskDomainObj) {
        TaskEntity taskEntity = taskEntityMapper.toEntity(taskDomainObj);
        TaskEntity savedObj = taskRepository.save(taskEntity);
        return taskEntityMapper.toDomainObj(savedObj);
    }
}
