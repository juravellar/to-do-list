package com.avellar.todolist.classes;

import com.avellar.todolist.infrastructure.persistence.TaskRepository;
import com.avellar.todolist.service.TaskService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;

@Configuration
@EnableR2dbcAuditing
public class TaskServiceConfig {
    @Bean
    TaskService taskService(TaskRepository taskRepository) {
        return new TaskService(taskRepository);
    }
}
