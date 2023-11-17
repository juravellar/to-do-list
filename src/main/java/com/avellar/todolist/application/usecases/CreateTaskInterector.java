package com.avellar.todolist.application.usecases;

import com.avellar.todolist.application.gateways.TaskGateway;
import com.avellar.todolist.domain.entity.Task;

public class CreateTaskInterector {
    private final TaskGateway taskGateway;

    public CreateTaskInterector(TaskGateway taskGateway) {
        this.taskGateway = taskGateway;
    }

    public Task createTask(Task task){
        return taskGateway.createTask(task);
    }
}
