package com.avelar.todolist;

import com.avelar.todolist.api.TaskRequest;
import com.avelar.todolist.domain.Task;
import com.avelar.todolist.domain.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Import(TestConfig.class)
public class TaskServiceTests {
  public static final Task TO_ARRANGE = new Task(
      1L, "Valid Name", "Arrumar Cama", false, false, null, null);

  @Autowired
  WebTestClient webTestClient;

  @Autowired
  TaskRepository taskRepository;

  @Test
  public void testCreatePlaceSuccess() {
    final String name = "Valid Name";
    final String description = "Valid description";
    final Boolean prioritized = false;
    final Boolean realized = false;

    webTestClient
        .post()
        .uri("/tasks")
        .bodyValue(
            new TaskRequest(name, description, prioritized, realized))
        .exchange()
        .expectStatus().isCreated()
        .expectBody()
        .jsonPath("name").isEqualTo(name)
        .jsonPath("description").isEqualTo(description)
        .jsonPath("prioritized").isEqualTo(prioritized)
        .jsonPath("realized").isEqualTo(realized)
        .jsonPath("createdAt").isNotEmpty()
        .jsonPath("updatedAt").isNotEmpty();
  }

  @Test
  public void testCreatePlaceFailure() {
    final String name = "";
    final String description = "";

    webTestClient
        .post()
        .uri("/tasks")
        .bodyValue(
            new TaskRequest(name, description, null, null))
        .exchange()
        .expectStatus().isBadRequest();
  }

  @Test
  public void testEditPlaceSuccess() {
    final String newName = "Valid Name";
    final String newDescription = "Valid description";
    final Boolean newPrioritized = true;
    final Boolean newRealized = true;

    // Updates name, description and prioritized.
    webTestClient
        .patch()
        .uri("/tasks/1")
        .bodyValue(
            new TaskRequest(newName, newDescription, newPrioritized, newRealized))
        .exchange()
        .expectStatus().isOk()
        .expectBody()
        .jsonPath("name").isEqualTo(newName)
        .jsonPath("description").isEqualTo(newDescription)
        .jsonPath("prioritized").isEqualTo(newPrioritized)
        .jsonPath("realized").isEqualTo(newRealized)
        .jsonPath("createdAt").isNotEmpty()
        .jsonPath("updatedAt").isNotEmpty();

    // Updates only name
    webTestClient
        .patch()
        .uri("/tasks/1")
        .bodyValue(
            new TaskRequest(TO_ARRANGE.name(), null, null, null))
        .exchange()
        .expectStatus().isOk()
        .expectBody()
        .jsonPath("name").isEqualTo(TO_ARRANGE.name())
        .jsonPath("description").isEqualTo(newDescription)
        .jsonPath("createdAt").isNotEmpty()
        .jsonPath("updatedAt").isNotEmpty();

    // Updates only description
    webTestClient
        .patch()
        .uri("/tasks/1")
        .bodyValue(
            new TaskRequest(null, TO_ARRANGE.description(), null, null))
        .exchange()
        .expectStatus().isOk()
        .expectBody()
        .jsonPath("name").isEqualTo(TO_ARRANGE.name())
        .jsonPath("description").isEqualTo(TO_ARRANGE.description())
        .jsonPath("createdAt").isNotEmpty()
        .jsonPath("updatedAt").isNotEmpty();

    // Updates only prioritized
    webTestClient
        .patch()
        .uri("/tasks/1")
        .bodyValue(
            new TaskRequest(null, null, TO_ARRANGE.prioritized(), null))
        .exchange()
        .expectStatus().isOk()
        .expectBody()
        .jsonPath("name").isEqualTo(TO_ARRANGE.name())
        .jsonPath("description").isEqualTo(TO_ARRANGE.description())
        .jsonPath("prioritized").isEqualTo(TO_ARRANGE.prioritized())
        .jsonPath("createdAt").isNotEmpty()
        .jsonPath("updatedAt").isNotEmpty();
  }

  @Test
  public void testGetSuccess() {
    webTestClient
        .get()
        .uri("/tasks/1")
        .exchange()
        .expectStatus().isOk()
        .expectBody()
        .jsonPath("name").isEqualTo(TO_ARRANGE.name())
        .jsonPath("description").isEqualTo(TO_ARRANGE.description())
        .jsonPath("prioritized").isEqualTo(TO_ARRANGE.prioritized())
        .jsonPath("realized").isEqualTo(TO_ARRANGE.realized())
        .jsonPath("createdAt").isNotEmpty()
        .jsonPath("updatedAt").isNotEmpty();
  }

  @Test
  public void testGetFailure() {
    webTestClient
        .get()
        .uri("/tasks/11")
        .exchange()
        .expectStatus().isNotFound();
  }

  @Test
  public void testListAllSuccess() {
    webTestClient
        .get()
        .uri("/tasks")
        .exchange()
        .expectStatus().isOk()
        .expectBody()
        .jsonPath("$").isArray()
        .jsonPath("$[0].name").isEqualTo(TO_ARRANGE.name())
        .jsonPath("$[0].description").isEqualTo(TO_ARRANGE.description())
        .jsonPath("$[0].prioritized").isEqualTo(TO_ARRANGE.prioritized())
        .jsonPath("$[0].realized").isEqualTo(TO_ARRANGE.realized())
        .jsonPath("$[0].createdAt").isNotEmpty()
        .jsonPath("$[0].updatedAt").isNotEmpty();
  }

  @Test
  public void testListByNameSuccess() {
    webTestClient
        .get()
        .uri("/tasks?name=%s".formatted(TO_ARRANGE.name()))
        .exchange()
        .expectStatus().isOk()
        .expectBody()
        .jsonPath("$").isArray()
        .jsonPath("$.length()").isEqualTo(1)
        .jsonPath("$[0].name").isEqualTo(TO_ARRANGE.name())
        .jsonPath("$[0].description").isEqualTo(TO_ARRANGE.description())
        .jsonPath("$[0].prioritized").isEqualTo(TO_ARRANGE.prioritized())
        .jsonPath("$[0].realized").isEqualTo(TO_ARRANGE.realized())
        .jsonPath("$[0].createdAt").isNotEmpty()
        .jsonPath("$[0].updatedAt").isNotEmpty();
  }

  @Test
  public void testListByNameNotFound() {
    webTestClient
        .get()
        .uri("/tasks?name=name")
        .exchange()
        .expectStatus().isOk()
        .expectBody()
        .jsonPath("$").isArray()
        .jsonPath("$.length()").isEqualTo(0);
  }
}
