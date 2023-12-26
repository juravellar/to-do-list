package com.avellar.todolist;


import com.avellar.todolist.infrastructure.controller.CreateTaskRequest;
import com.avellar.todolist.infrastructure.persistence.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Import(TestConfig.class)
public class TaskServiceTests {
    public static final Task ORGANIZAR_ARMARIO = new Task(
            1L, "Organizar", "Organizar Armario", false, false);

    @Autowired
    WebTestClient webTestClient;

    @Test
    public void testCreateTaskSuccess() {
        final Long id = 1L;
        final String name = "Valid Name";
        final String description = "Valid Description";
        final Boolean realized = false;
        final Boolean prioritized = false;

        webTestClient
                .post()
                .uri("/tasks")
                .bodyValue(
                        new CreateTaskRequest(id, name, description, realized, prioritized))
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("id").isEqualTo(id)
                .jsonPath("name").isEqualTo(name)
                .jsonPath("description").isEqualTo(description)
                .jsonPath("realized").isEqualTo(realized)
                .jsonPath("prioritized").isEqualTo(prioritized)
                .jsonPath("createdAt").isNotEmpty();
    }

    @Test
    public void testCreateTaskFailure() {
        final String name = "";
        final String description = "";

        webTestClient
                .post()
                .uri("/tasks")
                .bodyValue(
                        new CreateTaskRequest(null, name, description, null,null))
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    public void testEditTaskSuccess() {
        final Long newId = 2L;
        final String newName = "Roupa";
        final String newDescription = "Lavar roupa";
        final Boolean newPrioritized = true;
        final Boolean newRealized = true;

        webTestClient
                .patch()
                .uri("/tasks/1")
                .bodyValue(
                        new CreateTaskRequest(newId, newName, newDescription, newPrioritized, newRealized))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("id").isEqualTo(newId)
                .jsonPath("name").isEqualTo(newName)
                .jsonPath("description").isEqualTo(newDescription)
                .jsonPath("prioritized").isEqualTo(newPrioritized)
                .jsonPath("realized").isEqualTo(newRealized);

        webTestClient
                .patch()
                .uri("/tasks/1")
                .bodyValue(
                        new CreateTaskRequest(ORGANIZAR_ARMARIO.getId(),newName, newDescription, newPrioritized, newRealized))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("id").isEqualTo(ORGANIZAR_ARMARIO.getId())
                .jsonPath("name").isEqualTo(newName)
                .jsonPath("description").isEqualTo(newDescription)
                .jsonPath("prioritized").isEqualTo(newPrioritized)
                .jsonPath("realized").isEqualTo(newRealized);

        webTestClient
                .patch()
                .uri("/tasks/1")
                .bodyValue(
                        new CreateTaskRequest(ORGANIZAR_ARMARIO.getId(),ORGANIZAR_ARMARIO.getName(), newDescription, newPrioritized, newRealized))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("id").isEqualTo(ORGANIZAR_ARMARIO.getId())
                .jsonPath("name").isEqualTo(ORGANIZAR_ARMARIO.getName())
                .jsonPath("description").isEqualTo(newDescription)
                .jsonPath("prioritized").isEqualTo(newPrioritized)
                .jsonPath("realized").isEqualTo(newRealized);

        webTestClient
                .patch()
                .uri("/tasks/1")
                .bodyValue(
                        new CreateTaskRequest(ORGANIZAR_ARMARIO.getId(), ORGANIZAR_ARMARIO.getName(), ORGANIZAR_ARMARIO.getDescription(), newPrioritized,  newRealized))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("id").isEqualTo(ORGANIZAR_ARMARIO.getId())
                .jsonPath("name").isEqualTo(ORGANIZAR_ARMARIO.getName())
                .jsonPath("description").isEqualTo(ORGANIZAR_ARMARIO.getDescription())
                .jsonPath("prioritized").isEqualTo(newPrioritized)
                .jsonPath("realized").isEqualTo(newRealized);

        webTestClient
                .patch()
                .uri("/tasks/1")
                .bodyValue(
                        new CreateTaskRequest(ORGANIZAR_ARMARIO.getId(), ORGANIZAR_ARMARIO.getName(), ORGANIZAR_ARMARIO.getDescription(), ORGANIZAR_ARMARIO.getPrioritized(),  newRealized))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("id").isEqualTo(ORGANIZAR_ARMARIO.getId())
                .jsonPath("name").isEqualTo(ORGANIZAR_ARMARIO.getName())
                .jsonPath("description").isEqualTo(ORGANIZAR_ARMARIO.getDescription())
                .jsonPath("prioritized").isEqualTo(ORGANIZAR_ARMARIO.getPrioritized())
                .jsonPath("realized").isEqualTo(newRealized);

        webTestClient
                .patch()
                .uri("/tasks/1")
                .bodyValue(
                        new CreateTaskRequest(ORGANIZAR_ARMARIO.getId(), ORGANIZAR_ARMARIO.getName(), ORGANIZAR_ARMARIO.getDescription(), ORGANIZAR_ARMARIO.getPrioritized(),  ORGANIZAR_ARMARIO.getRealized()))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("id").isEqualTo(ORGANIZAR_ARMARIO.getId())
                .jsonPath("name").isEqualTo(ORGANIZAR_ARMARIO.getName())
                .jsonPath("description").isEqualTo(ORGANIZAR_ARMARIO.getDescription())
                .jsonPath("prioritized").isEqualTo(ORGANIZAR_ARMARIO.getPrioritized())
                .jsonPath("realized").isEqualTo(ORGANIZAR_ARMARIO.getRealized());
    }

    @Test
    public void testGetSuccess() {
        webTestClient
                .get()
                .uri("/tasks/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.[0].id").isEqualTo(ORGANIZAR_ARMARIO.getId())
                .jsonPath("$.[0].name").isEqualTo(ORGANIZAR_ARMARIO.getName())
                .jsonPath("$.[0].description").isEqualTo(ORGANIZAR_ARMARIO.getDescription())
                .jsonPath("$.[0].prioritized").isEqualTo(ORGANIZAR_ARMARIO.getPrioritized())
                .jsonPath("$.[0].realized").isEqualTo(ORGANIZAR_ARMARIO.getRealized());
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
                .jsonPath("$.[0].id").isEqualTo(ORGANIZAR_ARMARIO.getId())
                .jsonPath("$.[0].name").isEqualTo(ORGANIZAR_ARMARIO.getName())
                .jsonPath("$.[0].description").isEqualTo(ORGANIZAR_ARMARIO.getDescription())
                .jsonPath("$.[0].prioritized").isEqualTo(ORGANIZAR_ARMARIO.getPrioritized())
                .jsonPath("$.[0].realized").isEqualTo(ORGANIZAR_ARMARIO.getRealized());
    }

    @Test
    public void testListByNameSuccess() {
        webTestClient
                .get()
                .uri("/tasks?name=%s".formatted(ORGANIZAR_ARMARIO.getName()))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isArray()
                .jsonPath("$.length()").isEqualTo(1)
                .jsonPath("$.[0].id").isEqualTo(ORGANIZAR_ARMARIO.getId())
                .jsonPath("$.[0].name").isEqualTo(ORGANIZAR_ARMARIO.getName())
                .jsonPath("$.[0].description").isEqualTo(ORGANIZAR_ARMARIO.getDescription())
                .jsonPath("$.[0].prioritized").isEqualTo(ORGANIZAR_ARMARIO.getPrioritized())
                .jsonPath("$.[0].realized").isEqualTo(ORGANIZAR_ARMARIO.getRealized());
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