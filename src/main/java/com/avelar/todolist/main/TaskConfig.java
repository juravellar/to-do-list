package com.avelar.todolist.main;

import com.avelar.todolist.application.gateways.TaskGateway;
import com.avelar.todolist.application.usecases.CreateTaskInteractor;
import com.avelar.todolist.application.usecases.EditTaskInteractor;
import com.avelar.todolist.application.usecases.ListTaskInteractor;
import com.avelar.todolist.infrastructure.persistence.TaskRepository;
import com.avelar.todolist.infrastructure.controller.dto.TaskDTOMapper;
import com.avelar.todolist.infrastructure.gateways.TaskEntityMapper;
import com.avelar.todolist.infrastructure.gateways.TaskRepositoryGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TaskConfig {

  @Bean
  CreateTaskInteractor createTaskCase(TaskGateway taskGateway) {
    return new CreateTaskInteractor(taskGateway);
  }

  @Bean
  EditTaskInteractor editTaskCase(TaskGateway taskGateway) {
    return new EditTaskInteractor(taskGateway);
  }

  @Bean
  ListTaskInteractor listTaskCase(TaskGateway taskGateway) {
    return new ListTaskInteractor(taskGateway);
  }

  @Bean
  TaskGateway taskGateway(TaskRepository repository, TaskEntityMapper mapper) {
    return new TaskRepositoryGateway(repository, mapper);
  }

  @Bean
  TaskEntityMapper taskEntityMapper() {
    return new TaskEntityMapper();
  }

  @Bean
  TaskDTOMapper taskDTOMapper() {
    return new TaskDTOMapper();
  }
}
