package com.avellar.todolist.classes;

import com.avellar.todolist.infrastructure.controller.CreateTaskRequest;
import com.avellar.todolist.infrastructure.controller.CreateTaskResponse;
import com.avellar.todolist.model.Task;
import org.springframework.util.StringUtils;

public class TaskMapper {
    public static Task updateTaskFromDTO(CreateTaskRequest taskRequest, Task task) {
        final String name = StringUtils.hasText(taskRequest.name()) ? taskRequest.name() : task.name();
        final String description = StringUtils.hasText(taskRequest.description()) ? taskRequest.description() : task.description();
        final Boolean realized = taskRequest.realized() ? taskRequest.realized() : task.realized();
        final Boolean prioritized = taskRequest.prioritized() ? taskRequest.prioritized() : task.prioritized();
        return new Task(task.id(), name, description, prioritized, realized, task.createdAt(), task.updatedAt());
    }

    public static CreateTaskResponse toResponse(Task task) {
        return new CreateTaskResponse(task.name(), task.description(),
                task.prioritized(), task.realized());
    }

}