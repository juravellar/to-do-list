package com.avellar.todolist.infrastructure.gateways;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.avellar.todolist.domain.entity.TaskPort;
import com.avellar.todolist.infrastructure.persistence.Task;
import com.avellar.todolist.infrastructure.persistence.TaskRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest()
@ActiveProfiles("test")
public class TaskRepositoryGatewayTest {

  @Mock
  TaskRepository taskRepository;
  @Mock
  TaskEntityMapper mapper;

  TaskRepositoryGateway taskRepositoryGateway;

  @BeforeEach
  void setUp() throws Exception {
    MockitoAnnotations.openMocks(this);

    taskRepositoryGateway = new TaskRepositoryGateway(taskRepository, mapper);
  }

  @Test
  public void createTask() throws Exception {
    var task = getTask();
    var taskPort = getTaskPort();
    when(mapper.toEntity(any())).thenReturn(task);
    when(taskRepository.save(any())).thenReturn(task);
    when(mapper.toDomainObj(any())).thenReturn(taskPort);

    var taskReturn = taskRepositoryGateway.createTask(getTaskPort());

    assertAll(
        () -> assertEquals(Objects.requireNonNull(taskReturn).id(), task.getId()),
        () -> assertEquals(Objects.requireNonNull(taskReturn).description(), task.getDescription())
    );
  }

  @Test
  public void editTask() throws Exception {
    var task = getTask();
    var taskPort = getTaskPort();

    when(mapper.toEntity(any())).thenReturn(task);
    when(taskRepository.findById(any())).thenReturn(Optional.of(task));
    when(taskRepository.save(any())).thenReturn(task);
    when(mapper.toDomainObj(any())).thenReturn(taskPort);

    var taskReturn = taskRepositoryGateway.editTask(task.getId(), taskPort);

    assertAll(
            () -> assertEquals(Objects.requireNonNull(taskReturn).id(), task.getId()),
            () -> assertEquals(Objects.requireNonNull(taskReturn).description(), task.getDescription())
    );
  }

  @Test
  public void getTaskById() throws Exception {
    var task = getTask();
    var taskPort = getTaskPort();

    when(mapper.toEntity(any())).thenReturn(task);
    when(taskRepository.findById(any())).thenReturn(Optional.of(task));
    when(mapper.toDomainObj(any())).thenReturn(taskPort);

    var taskReturn = taskRepositoryGateway.getById(task.getId());

    assertAll(
            () -> assertEquals(Objects.requireNonNull(taskReturn).id(), task.getId()),
            () -> assertEquals(Objects.requireNonNull(taskReturn).description(), task.getDescription())
    );
  }

  @Test
  public void listTaskByName() throws Exception {

    var task = getTask();
    var taskPort = getTaskPort();

    when(mapper.toEntity(any())).thenReturn(task);
    when(taskRepository.findByNameContaining(any())).thenReturn(Optional.of(task));
    when(mapper.toDomainObj(any())).thenReturn(taskPort);

    ResponseEntity<List<TaskPort>> taskReturn = (ResponseEntity<List<TaskPort>>) taskRepositoryGateway.getByName(task.getName());

    assertAll("Verifications for listTaskByName",
            () -> assertNotNull(taskReturn, "The answer must not be null"),
            () -> assertEquals(HttpStatus.OK, taskReturn.getStatusCode(), "The status must be OK"),
            () -> assertNotNull(taskReturn.getBody(), "The body of the answer must not be null")
    );
  }

  private Task getTask() {
    return Task.builder()
        .id(99L)
        .name("Organizar")
        .description("Organizar Armario")
        .prioritized(Boolean.FALSE)
        .realized(Boolean.FALSE)
        .createdAt(LocalDateTime.now())
        .build();
  }

  private TaskPort getTaskPort() {
    return new TaskPort(99L, "Organizar", "Organizar Armario", false, false, LocalDateTime.now(),
        null);
  }

}
