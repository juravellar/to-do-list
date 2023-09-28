package com.avellar.todolist.web;

import com.avellar.todolist.api.TaskRequest;
import com.avellar.todolist.api.TaskResponse;
import com.avellar.todolist.domain.TaskMapper;
import com.avellar.todolist.domain.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<Mono<TaskResponse>> create(@Valid @RequestBody TaskRequest request) {
        var taskResponse = taskService.create(request).map(TaskMapper::toResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(taskResponse);
    }

    @PatchMapping("{id}")
    public Mono<TaskResponse> edit(@PathVariable("id") Long id, @RequestBody TaskRequest request) {
        return taskService.edit(id, request).map(TaskMapper::toResponse);
    }

    @GetMapping("{id}")
    public Mono<ResponseEntity<TaskResponse>> get(@PathVariable("id") Long id) {
        return taskService.get(id)
                .map(task -> ResponseEntity.ok(TaskMapper.toResponse(task)))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping
    public Flux<TaskResponse> list(@RequestParam(required = false) String name) {
        return taskService.list(name).map(TaskMapper::toResponse);
    }


}
