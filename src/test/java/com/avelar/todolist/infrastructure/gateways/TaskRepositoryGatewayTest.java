package com.avelar.todolist.infrastructure.gateways;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.avelar.todolist.domain.entity.TaskPort;
import com.avelar.todolist.infrastructure.controller.dto.TaskResponse;
import com.avelar.todolist.infrastructure.persistence.Task;
import com.avelar.todolist.infrastructure.persistence.TaskRepository;
import java.time.LocalDateTime;
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
