package com.avellar.todolist.application.gateways;

import com.avellar.todolist.domain.entity.Task;

public interface TaskGateway {
    Task createTask (Task task);
}
