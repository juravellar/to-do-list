package com.avellar.todolist.classes;

import com.avellar.todolist.infrastructure.controller.CreateTaskRequest;
import com.avellar.todolist.infrastructure.controller.CreateTaskResponse;
import com.avellar.todolist.infrastructure.persistence.Task;
import org.springframework.util.StringUtils;

public class TaskMapper {

    public static com.avellar.todolist.domain.entity.Task updateTaskFromDTO(CreateTaskRequest taskRequest, Task task) {
        final Long id = taskRequest.id() != null ? taskRequest.id() : task.getId();
        final String name = StringUtils.hasText(taskRequest.name()) ? taskRequest.name() : task.getName();
        final String description = StringUtils.hasText(taskRequest.description()) ? taskRequest.description() : task.getDescription();
        final Boolean realized = taskRequest.realized() != null ? taskRequest.realized() : task.getRealized();
        final Boolean prioritized = taskRequest.prioritized() != null ? taskRequest.prioritized() : task.getPrioritized();
        return new com.avellar.todolist.domain.entity.Task(id, name, description, prioritized, realized);
    }

    public static CreateTaskResponse toResponse(com.avellar.todolist.domain.entity.Task task) {
        return new CreateTaskResponse(task.id(), task.name(), task.description(),
                task.prioritized(), task.realized());
    }

    public static CreateTaskResponse toResponse(Task task) {
        return new CreateTaskResponse(task.getId(), task.getName(), task.getDescription(),
                task.getPrioritized(), task.getRealized());
    }
}