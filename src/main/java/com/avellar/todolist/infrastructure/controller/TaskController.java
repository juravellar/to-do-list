package com.avellar.todolist.infrastructure.controller;

import com.avellar.todolist.application.usecases.CreateTaskInteractor;
import com.avellar.todolist.classes.TaskMapper;
import com.avellar.todolist.domain.entity.Task;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.avellar.todolist.service.TaskService;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final CreateTaskInteractor createTaskInteractor;
    private final TaskDTOMapper taskDTOMapper;

    public TaskController(CreateTaskInteractor createTaskInteractor, TaskDTOMapper taskDTOMapper) {
        this.createTaskInteractor = createTaskInteractor;
        this.taskDTOMapper = taskDTOMapper;
    }

    @PostMapping
    public CreateTaskResponse create(@Valid @RequestBody CreateTaskRequest request) {
        Task taskBusinessObj = TaskDTOMapper.toTask(request);
        Task task = createTaskInteractor.createTask(taskBusinessObj);
        return taskDTOMapper.toResponse(task);
    }

    @PatchMapping("{id}")
    public Mono<ResponseEntity<CreateTaskResponse>> edit(@PathVariable("id") Long id, @RequestBody CreateTaskRequest request) {
        return createTaskInteractor.edit(id, request)
                .map(TaskMapper::toResponse)
                .map(response -> ResponseEntity.ok().body(response))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("{id}")
    public Mono<ResponseEntity<CreateTaskResponse>> get(@PathVariable("id") Long id) {
        return createTaskInteractor.get(id)
                .map(task -> ResponseEntity.ok(TaskMapper.toResponse(task)))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping
    public Flux<CreateTaskResponse> listTasks(@RequestParam(required = false) String name) {
        return TaskService.list(name)
                .map(task -> CreateTaskResponse.builder()
                        .id(task.getId())
                        .name(task.getName())
                        .description(task.getDescription())
                        .prioritized(task.getPrioritized())
                        .build());
    }
}



}
