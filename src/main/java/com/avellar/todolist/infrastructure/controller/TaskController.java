package com.avellar.todolist.infrastructure.controller;

import com.avellar.todolist.application.usecases.CompleteTask;
import com.avellar.todolist.application.usecases.CreateTaskInteractor;
import com.avellar.todolist.application.usecases.EditTaskInteractor;
import com.avellar.todolist.application.usecases.ListTaskInteractor;
import com.avellar.todolist.domain.entity.TaskPort;
import com.avellar.todolist.infrastructure.controller.dto.TaskDTOMapper;
import com.avellar.todolist.infrastructure.controller.dto.TaskRequest;
import com.avellar.todolist.infrastructure.controller.dto.TaskResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tasks")
@AllArgsConstructor
public class TaskController {

  private final CreateTaskInteractor createTaskUseCase;
  private final EditTaskInteractor editTaskInteractor;
  private final ListTaskInteractor listTaskInteractor;
  private final CompleteTask completeTask;
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

  @GetMapping("/find")
  public ResponseEntity<List<TaskResponse>> listTaskByName(@RequestParam(required = false) String name) {
    var taskPort = listTaskInteractor.getByName(name);
    return ResponseEntity.status(HttpStatus.OK).body(
        taskPort.stream().map(mapper::toResponse).toList());
  }

  @GetMapping("/priority")
  public ResponseEntity<List<TaskResponse>> getTaskByPriority(
          @RequestParam(required = false) Boolean prioritized,
          @RequestParam(required = false) LocalDateTime createdAt) {

    List<TaskPort> taskPortList = listTaskInteractor.getTaskByPriority(prioritized, createdAt);

    List<TaskResponse> taskResponseList = taskPortList.stream()
            .map(mapper::toResponse)
            .collect(Collectors.toList());

    return ResponseEntity.status(HttpStatus.OK).body(taskResponseList);
  }

  @PatchMapping("/complete/{taskId}")
  public ResponseEntity<TaskResponse> completeTask(@PathVariable Long taskId) {
    TaskPort completedTask = completeTask.completeTask(taskId);
    return ResponseEntity.status(HttpStatus.OK).body(mapper.toResponse(completedTask));
  }

}
