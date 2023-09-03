package com.avellar.todolist.domain;

import com.avellar.todolist.api.TaskRequest;
import com.avellar.todolist.api.TaskResponse;
import org.springframework.util.StringUtils;

public class TaskMapper {
    public static Task updatePlaceFromDTO(TaskRequest taskRequest, Task task) {
        final String name = StringUtils.hasText(taskRequest.name()) ? taskRequest.name() : task.name();
        final String description = StringUtils.hasText(taskRequest.description()) ? taskRequest.description() : task.description();
        return new Task(task.id(), name, task.slug(), description, task.done(), task.preference());
    }

    public static TaskResponse toResponse(Task task) {
        return new TaskResponse(task.name(), task.slug(),
                task.description(), task.done(), task.preference());
    }

}