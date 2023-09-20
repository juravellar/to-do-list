package com.avellar.todolist.domain;

import com.avellar.todolist.api.TaskRequest;
import com.avellar.todolist.util.QueryBuilder;
import com.github.slugify.Slugify;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class TaskService {
    private final TaskRepository taskRepository;
    private final Slugify slg;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
        this.slg = Slugify.builder().build();
    }

    public Mono<Task> create(TaskRequest taskRequest) {
        var task = new Task(
                null, taskRequest.name(), taskRequest.description(),taskRequest.prioritized(), taskRequest.realized(), null, null);
        return taskRepository.save(task);
    }

    public Mono<Task> edit(Long id, TaskRequest taskRequest) {
        return taskRepository.findById(id)
                .map(task -> TaskMapper.updateTaskFromDTO(taskRequest, task))
                .flatMap(taskRepository::save);
    }

    public Mono<Task> get(Long id) {
        return taskRepository.findById(id);
    }

    public Flux<Task> list(String name) {
        var task = new Task(null, name, null, null, null, null, null);
        Example<Task> query = QueryBuilder.makeQuery(task);
        return taskRepository.findAll(query, Sort.by("name").ascending());
    }
}
