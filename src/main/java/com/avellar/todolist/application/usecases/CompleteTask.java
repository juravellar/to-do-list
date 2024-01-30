package com.avellar.todolist.application.usecases;

import com.avellar.todolist.application.gateways.TaskGateway;
import com.avellar.todolist.domain.entity.TaskPort;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CompleteTask {

    private final TaskGateway taskGateway;

    public TaskPort completeTask(Long id){
        return taskGateway.completeTask(id);
    }
}
