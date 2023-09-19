package com.avelar.todolist.web;

import com.avelar.todolist.api.TaskRequest;
import com.avelar.todolist.api.TaskResponse;
import com.avelar.todolist.domain.TaskMapper;
import com.avelar.todolist.domain.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/tasks")
public class TaskController {

  @Autowired
  private TaskService taskService;

  @PostMapping
  public ResponseEntity<Mono<TaskResponse>> create(@Valid @RequestBody TaskRequest request) {
    var placeResponse = taskService.create(request).map(TaskMapper::toResponse);
    return ResponseEntity.status(HttpStatus.CREATED).body(placeResponse);
  }

  @PatchMapping("{id}")
  public Mono<TaskResponse> edit(@PathVariable("id") Long id, @RequestBody TaskRequest request) {
    return taskService.edit(id, request).map(TaskMapper::toResponse);
  }

  @GetMapping("{id}")
  public Mono<ResponseEntity<TaskResponse>> get(@PathVariable("id") Long id) {
    return taskService.get(id)
      .map(place -> ResponseEntity.ok(TaskMapper.toResponse(place)))
      .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @GetMapping
  public Flux<TaskResponse> list(@RequestParam(required = false) String name) {
    return taskService.list(name).map(TaskMapper::toResponse);
  }

}
