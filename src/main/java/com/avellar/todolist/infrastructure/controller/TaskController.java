package com.avellar.todolist.infrastructure.controller;

import com.avellar.todolist.application.usecases.CreateTaskInterector;
import com.avellar.todolist.classes.TaskMapper;
import com.avellar.todolist.domain.entity.Task;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.avellar.todolist.service.TaskService;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private CreateTaskInterector createTaskInterector;
    private final TaskDTOMapper taskDTOMapper;

    public TaskController(CreateTaskInterector createTaskInterector, TaskDTOMapper taskDTOMapper) {
        this.createTaskInterector = createTaskInterector;
        this.taskDTOMapper = taskDTOMapper;
    }

    @PostMapping
    public CreateTaskResponse create(@Valid @RequestBody CreateTaskRequest request) {
        Task taskBusinessObj = TaskDTOMapper.toTask(request);
        Task task = createTaskInterector.createTask(taskBusinessObj);
        return taskDTOMapper.toResponse(task);
    }

    @PatchMapping("{id}")
    public Mono<CreateTaskResponse> edit(@PathVariable("id") Long id, @RequestBody CreateTaskRequest request) {
        return createTaskInterector.edit(id, request).map(TaskMapper::toResponse);
    }

    @GetMapping("{id}")
    public Mono<ResponseEntity<CreateTaskResponse>> get(@PathVariable("id") Long id) {
        return createTaskInterector.get(id)
                .map(task -> ResponseEntity.ok(TaskMapper.toResponse(task)))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping
    public Flux<CreateTaskResponse> listTasks(@RequestParam(required = false) String name) {
        return TaskService.list(name)
                .map(task -> CreateTaskResponse.builder()
                        .id(task.getClass())
                        .name(task.getClass())
                        .description(task.getClass())
                        .dueDate(task.getClass())
                        .build());
    }


}
