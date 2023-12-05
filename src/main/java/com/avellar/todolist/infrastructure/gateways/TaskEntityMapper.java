package com.avellar.todolist.infrastructure.gateways;

import com.avellar.todolist.domain.entity.Task;
import com.avellar.todolist.infrastructure.persistence.TaskEntity;

import java.time.LocalDateTime;

public class TaskEntityMapper {
    TaskEntity toEntity (Task taskDomainObj){
        return new TaskEntity(taskDomainObj.id(), taskDomainObj.name(), taskDomainObj.description(), taskDomainObj.realized(), taskDomainObj.prioritized());
    }

    Task toDomainObj(TaskEntity taskEntity){
        return new Task(taskEntity.getId(), taskEntity.getName(), taskEntity.getDescription(), taskEntity.getRealized(), taskEntity.getPrioritized());
    }
}
