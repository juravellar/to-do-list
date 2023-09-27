package com.avellar.todolist;


import com.avellar.todolist.api.TaskRequest;
import com.avellar.todolist.domain.Task;
import com.avellar.todolist.domain.TaskRepository;
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
            1L, "Valid Name", "Organizar armario", false, false, null, null);

    @Autowired
    WebTestClient webTestClient;
    @Autowired
    TaskRepository taskRepository;

    @Test
    public void testCreateTaskSuccess() {
        final String name = "Valid Name";
        final String description = "Valid Description";
        final Boolean realized = false;
        final Boolean prioritized = false;

        webTestClient
                .post()
                .uri("/tasks")
                .bodyValue(
                        new TaskRequest(name, description, realized, prioritized))
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
    public void testCreateTaskFailure() {
        final String name = "";
        final String description = "";

        webTestClient
                .post()
                .uri("/tasks")
                .bodyValue(
                        new TaskRequest(name, description, null,null))
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    public void testEditTaskSuccess() {
        final String newName = "Valid Name";
        final String newDescription = "New Description";
        final Boolean newPrioritized = true;
        final Boolean newRealized = true;

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

        webTestClient
                .patch()
                .uri("/tasks/1")
                .bodyValue(
                        new TaskRequest(ORGANIZAR_ARMARIO.name(), null, null, null))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("name").isEqualTo(ORGANIZAR_ARMARIO.name())
                .jsonPath("description").isEqualTo(newDescription)
                .jsonPath("createdAt").isNotEmpty()
                .jsonPath("updatedAt").isNotEmpty();

        webTestClient
                .patch()
                .uri("/tasks/1")
                .bodyValue(
                        new TaskRequest(null, ORGANIZAR_ARMARIO.description(), null,  null))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("name").isEqualTo(ORGANIZAR_ARMARIO.name())
                .jsonPath("description").isEqualTo(ORGANIZAR_ARMARIO.description())
                .jsonPath("createdAt").isNotEmpty()
                .jsonPath("updatedAt").isNotEmpty();

        webTestClient
                .patch()
                .uri("/tasks/1")
                .bodyValue(
                        new TaskRequest(null, null, null, ORGANIZAR_ARMARIO.prioritized()))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("name").isEqualTo(ORGANIZAR_ARMARIO.name())
                .jsonPath("description").isEqualTo(ORGANIZAR_ARMARIO.description())
                .jsonPath("prioritized").isEqualTo(ORGANIZAR_ARMARIO.prioritized())
                .jsonPath("createdAt").isNotEmpty()
                .jsonPath("updatedAt").isNotEmpty();

        webTestClient
                .patch()
                .uri("/tasks/1")
                .bodyValue(
                        new TaskRequest(null, null, ORGANIZAR_ARMARIO.realized(),  null))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("name").isEqualTo(ORGANIZAR_ARMARIO.name())
                .jsonPath("description").isEqualTo(ORGANIZAR_ARMARIO.description())
                .jsonPath("prioritized").isEqualTo(ORGANIZAR_ARMARIO.prioritized())
                .jsonPath("realized").isEqualTo(ORGANIZAR_ARMARIO.realized())
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
                .jsonPath("name").isEqualTo(ORGANIZAR_ARMARIO.name())
                .jsonPath("description").isEqualTo(ORGANIZAR_ARMARIO.description())
                .jsonPath("prioritized").isEqualTo(ORGANIZAR_ARMARIO.prioritized())
                .jsonPath("realized").isEqualTo(ORGANIZAR_ARMARIO.realized())
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
                .jsonPath("$[0].name").isEqualTo(ORGANIZAR_ARMARIO.name())
                .jsonPath("$[0].description").isEqualTo(ORGANIZAR_ARMARIO.description())
                .jsonPath("$[0].prioritized").isEqualTo(ORGANIZAR_ARMARIO.prioritized())
                .jsonPath("$[0].realized").isEqualTo(ORGANIZAR_ARMARIO.realized())
                .jsonPath("$[0].createdAt").isNotEmpty()
                .jsonPath("$[0].updatedAt").isNotEmpty();
    }

    @Test
    public void testListByNameSuccess() {
        webTestClient
                .get()
                .uri("/tasks?name=%s".formatted(ORGANIZAR_ARMARIO.name()))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isArray()
                .jsonPath("$.length()").isEqualTo(1)
                .jsonPath("$[0].name").isEqualTo(ORGANIZAR_ARMARIO.name())
                .jsonPath("$[0].description").isEqualTo(ORGANIZAR_ARMARIO.description())
                .jsonPath("$[0].prioritized").isEqualTo(ORGANIZAR_ARMARIO.prioritized())
                .jsonPath("$[0].realized").isEqualTo(ORGANIZAR_ARMARIO.realized())
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