package com.avellar.todolist.infrastructure.controller;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.avellar.todolist.application.usecases.CreateTaskInteractor;
import com.avellar.todolist.application.usecases.EditTaskInteractor;
import com.avellar.todolist.application.usecases.ListTaskInteractor;
import com.avellar.todolist.domain.entity.TaskPort;
import com.avellar.todolist.infrastructure.controller.dto.TaskDTOMapper;
import com.avellar.todolist.infrastructure.controller.dto.TaskRequest;
import com.avellar.todolist.infrastructure.controller.dto.TaskResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class TaskControllerTest {

  @Mock
  CreateTaskInteractor createTaskUseCase;
  @Mock
  EditTaskInteractor editTaskInteractor;
  @Mock
  ListTaskInteractor listTaskInteractor;
  @Mock
  TaskDTOMapper mapper;

  TaskController taskController;

  @BeforeEach
  void setUp() throws Exception {
    MockitoAnnotations.openMocks(this);

    taskController = new TaskController(createTaskUseCase, editTaskInteractor, listTaskInteractor,
        mapper);
  }

  @Test
  public void createTask() throws Exception {
    MockHttpServletRequest request = new MockHttpServletRequest();
    RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    var taskPort = getTaskPort();
    var taskResponse = getTaskResponse();
    when(mapper.totaskPort(any())).thenReturn(taskPort);
    when(createTaskUseCase.createTask(any())).thenReturn(taskPort);
    when(mapper.toResponse(any())).thenReturn(getTaskResponse());

    ResponseEntity<TaskResponse> responseEntity = taskController.createTask(getTaskRequest());

    assertAll(
        () -> assertEquals(Objects.requireNonNull(responseEntity).getStatusCode(),
            HttpStatusCode.valueOf(201)),
        () -> assertEquals(Objects.requireNonNull(responseEntity)
            .getBody().id(), taskResponse.id()),
        () -> assertEquals(Objects.requireNonNull(responseEntity)
            .getBody().description(), taskResponse.description())
    );
  }

  @Test
  public void editTask() throws Exception {
    MockHttpServletRequest request = new MockHttpServletRequest();
    RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    var taskPort = getTaskPort();
    var taskResponse = getTaskResponse();
    when(mapper.totaskPort(any())).thenReturn(taskPort);
    when(editTaskInteractor.editTask(taskPort.id(), taskPort)).thenReturn(taskPort);
    when(mapper.toResponse(any())).thenReturn(getTaskResponse());

    ResponseEntity<TaskResponse> responseEntity = taskController.editTask(taskPort.id(), getTaskRequest());

    assertAll(
            () -> assertEquals(Objects.requireNonNull(responseEntity).getStatusCode(),
                    HttpStatusCode.valueOf(200)),
            () -> assertEquals(Objects.requireNonNull(responseEntity)
                    .getBody().id(), taskResponse.id()),
            () -> assertEquals(Objects.requireNonNull(responseEntity)
                    .getBody().description(), taskResponse.description())
    );
  }

  @Test
  public void getTaskById() throws Exception {
    MockHttpServletRequest request = new MockHttpServletRequest();
    RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    var taskPort = getTaskPort();
    var taskResponse = getTaskResponse();
    when(mapper.totaskPort(any())).thenReturn(taskPort);
    when(listTaskInteractor.getById(taskPort.id())).thenReturn(taskPort);
    when(mapper.toResponse(any())).thenReturn(getTaskResponse());

    ResponseEntity<TaskResponse> responseEntity = taskController.getTaskById(taskPort.id());

    assertAll(
            () -> assertEquals(Objects.requireNonNull(responseEntity).getStatusCode(),
                    HttpStatusCode.valueOf(200)),
            () -> assertEquals(Objects.requireNonNull(responseEntity)
                    .getBody().id(), taskResponse.id()),
            () -> assertEquals(Objects.requireNonNull(responseEntity)
                    .getBody().description(), taskResponse.description())
    );
  }

  @Test
  public void listTaskByName() throws Exception {
    // Arrange
    MockHttpServletRequest request = new MockHttpServletRequest();
    RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

    var taskPort = getTaskPort();
    var taskResponse = getTaskResponse();
    List<TaskPort> taskPorts = new ArrayList<>();

    when(mapper.totaskPort(any())).thenReturn(taskPort);
    when(listTaskInteractor.getByName(taskPort.name())).thenReturn(taskPorts);
    when(mapper.toResponse(any())).thenReturn(getTaskResponse());

    ResponseEntity<List<TaskResponse>> responseEntity = taskController.listTaskByName(taskResponse.name());

    assertAll(
            () -> assertEquals(Objects.requireNonNull(responseEntity).getStatusCode(),
                    HttpStatusCode.valueOf(200)),
            () -> assertEquals(Objects.requireNonNull(responseEntity)
                    .getBody().id(), taskResponse.id()),
            () -> assertEquals(Objects.requireNonNull(responseEntity)
                    .getBody().description(), taskResponse.description())
    );
  }


  private TaskPort getTaskPort() {
    return new TaskPort(99L, "Organizar", "Organizar Armario", false, false, LocalDateTime.now(),
        null);
  }

  private TaskRequest getTaskRequest() {
    return new TaskRequest("Organizar", "Organizar Armario", false, false);
  }

  private TaskResponse getTaskResponse() {
    return new TaskResponse(99L, "Organizar", "Organizar Armario", false, false,
        LocalDateTime.now(), null);
  }
}
