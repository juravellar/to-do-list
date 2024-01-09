package com.avelar.todolist.infrastructure.controller;

import com.avelar.todolist.application.usecases.CreateTaskInteractor;
import com.avelar.todolist.application.usecases.EditTaskInteractor;
import com.avelar.todolist.application.usecases.ListTaskInteractor;
import com.avelar.todolist.infrastructure.controller.dto.TaskDTOMapper;
import com.avelar.todolist.infrastructure.controller.dto.TaskRequest;
import com.avelar.todolist.infrastructure.controller.dto.TaskResponse;
import java.util.List;
import lombok.AllArgsConstructor;
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

@RestController
@RequestMapping("/tasks")
@AllArgsConstructor
public class TaskController {

  private final CreateTaskInteractor createTaskUseCase;
  private final EditTaskInteractor editTaskInteractor;
  private final ListTaskInteractor listTaskInteractor;
  private final TaskDTOMapper mapper;

  @PostMapping
  public ResponseEntity<TaskResponse> createTask(@RequestBody TaskRequest request) {
    var taskPort = createTaskUseCase.createTask(mapper.totaskPort(request));
    return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(taskPort));
  }

  @PatchMapping("{id}")
  public  ResponseEntity<TaskResponse> editTask(@PathVariable("id") Long id, @RequestBody TaskRequest request) {
    var taskPort = editTaskInteractor.editTask(id, mapper.totaskPort(request));
    return ResponseEntity.status(HttpStatus.OK).body(mapper.toResponse(taskPort));
  }

  @GetMapping("{id}")
  public ResponseEntity<TaskResponse> getTaskById(@PathVariable("id") Long id) {
    var taskPort = listTaskInteractor.getById(id);
    return ResponseEntity.status(HttpStatus.OK).body(mapper.toResponse(taskPort));
  }

  @GetMapping
  public ResponseEntity<List<TaskResponse>> listTaskByName(@RequestParam(required = false) String name) {
    var taskPort = listTaskInteractor.getByName(name);
    return ResponseEntity.status(HttpStatus.OK).body(
        taskPort.stream().map(mapper::toResponse).toList());
  }

}
