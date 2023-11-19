package com.avellar.todolist.infrastructure.controller;

import com.avellar.todolist.domain.entity.Task;

public class TaskDTOMapper {
    CreateTaskResponse toResponse (Task task) {
        return new CreateTaskResponse(task.name(), task.description(), task.realized(), task.prioritized());
    }

    public static Task toTask(CreateTaskRequest request){
        return new Task(request.name(), request.description(), request.realized(), request.prioritized());
    }
}
