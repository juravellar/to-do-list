package com.avellar.todolist.infrastructure.gateways;

import com.avellar.todolist.infrastructure.persistence.Task;

public class TaskEntityMapper {
    Task toEntity (com.avellar.todolist.domain.entity.Task taskDomainObj){
        return new Task(taskDomainObj.id(), taskDomainObj.name(), taskDomainObj.description(), taskDomainObj.realized(), taskDomainObj.prioritized());
    }

    com.avellar.todolist.domain.entity.Task toDomainObj(Task task){
        return new com.avellar.todolist.domain.entity.Task(task.getId(), task.getName(), task.getDescription(), task.getRealized(), task.getPrioritized());
    }
}
