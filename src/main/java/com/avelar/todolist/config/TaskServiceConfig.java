package com.avelar.todolist.config;

import com.avelar.todolist.domain.TaskRepository;
import com.avelar.todolist.domain.TaskService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;

@Configuration
@EnableR2dbcAuditing
public class TaskServiceConfig {

  @Bean
  TaskService placeService(TaskRepository taskRepository) {
    return new TaskService(taskRepository);
  }
}
