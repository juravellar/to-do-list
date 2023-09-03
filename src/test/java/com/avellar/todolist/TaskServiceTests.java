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
            1L, "Organizar armario", "organizar-armario", "tirar poeira",false, true);

    @Autowired
    WebTestClient webTestClient;

    @Test
    public void testCreateTaskSuccess() {
        final String name = "Valid Name";
        final String description = "Valid Description";
        final String slug = "valid-name";

        webTestClient
                .post()
                .uri("/tasks")
                .bodyValue(
                        new TaskRequest(name, description, null, null))
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("name").isEqualTo(name)
                .jsonPath("description").isEqualTo(description)
                .jsonPath("slug").isEqualTo(slug)
                .jsonPath("done").isNotEmpty()
                .jsonPath("preference").isNotEmpty();
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
        final String newName = "New Name";
        final String newDescription = "New Description";
        final String newSlug = "new-name";

        webTestClient
                .patch()
                .uri("/tasks/1")
                .bodyValue(
                        new TaskRequest(newName, newDescription, null, null))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("name").isEqualTo(newName)
                .jsonPath("description").isEqualTo(newDescription)
                .jsonPath("slug").isEqualTo(newSlug)
                .jsonPath("done").isNotEmpty()
                .jsonPath("preference").isNotEmpty();

        // Updates only name
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
                .jsonPath("slug").isEqualTo(ORGANIZAR_ARMARIO.slug())
                .jsonPath("done").isNotEmpty()
                .jsonPath("preference").isNotEmpty();

        // Updates only city
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
                .jsonPath("slug").isEqualTo(ORGANIZAR_ARMARIO.slug())
                .jsonPath("done").isNotEmpty()
                .jsonPath("preference").isNotEmpty();

        // Updates only state
        webTestClient
                .patch()
                .uri("/tasks/1")
                .bodyValue(
                        new TaskRequest(null, null, false,  null))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("name").isEqualTo(ORGANIZAR_ARMARIO.name())
                .jsonPath("description").isEqualTo(ORGANIZAR_ARMARIO.description())
                .jsonPath("slug").isEqualTo(ORGANIZAR_ARMARIO.slug())
                .jsonPath("done").isNotEmpty()
                .jsonPath("preference").isNotEmpty();

        webTestClient
                .patch()
                .uri("/tasks/1")
                .bodyValue(
                        new TaskRequest(null, null, null,  true))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("name").isEqualTo(ORGANIZAR_ARMARIO.name())
                .jsonPath("description").isEqualTo(ORGANIZAR_ARMARIO.description())
                .jsonPath("slug").isEqualTo(ORGANIZAR_ARMARIO.slug())
                .jsonPath("done").isNotEmpty()
                .jsonPath("preference").isNotEmpty();
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
                .jsonPath("slug").isEqualTo(ORGANIZAR_ARMARIO.slug())
                .jsonPath("done").isNotEmpty()
                .jsonPath("preference").isNotEmpty();
    }

    @Test
    public void testGetFailure() {
        webTestClient
                .get()
                .uri("/tasks/1")
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
                .jsonPath("$[0].slug").isEqualTo(ORGANIZAR_ARMARIO.slug())
                .jsonPath("$[0].description").isEqualTo(ORGANIZAR_ARMARIO.description())
                .jsonPath("$[0].done").isNotEmpty()
                .jsonPath("$[0].preference").isNotEmpty();
    }

    @Test
    public void testListByNameSuccess() {
        webTestClient
                .get()
                .uri("/places?name=%s".formatted(ORGANIZAR_ARMARIO.name()))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isArray()
                .jsonPath("$.length()").isEqualTo(1)
                .jsonPath("$[0].name").isEqualTo(ORGANIZAR_ARMARIO.name())
                .jsonPath("$[0].slug").isEqualTo(ORGANIZAR_ARMARIO.slug())
                .jsonPath("$[0].description").isEqualTo(ORGANIZAR_ARMARIO.description())
                .jsonPath("$[0].done").isNotEmpty()
                .jsonPath("$[0].preference").isNotEmpty();
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