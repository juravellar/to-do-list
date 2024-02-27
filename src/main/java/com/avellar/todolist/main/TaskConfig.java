package com.avellar.todolist.main;

import com.avellar.todolist.application.gateways.TaskGateway;
import com.avellar.todolist.application.usecases.CompleteTask;
import com.avellar.todolist.application.usecases.CreateTaskInteractor;
import com.avellar.todolist.application.usecases.EditTaskInteractor;
import com.avellar.todolist.application.usecases.ListTaskInteractor;
import com.avellar.todolist.domain.entity.TaskPort;
import com.avellar.todolist.infrastructure.controller.dto.TaskDTOMapper;
import com.avellar.todolist.infrastructure.gateways.TaskEntityMapper;
import com.avellar.todolist.infrastructure.gateways.TaskRepositoryGateway;
import com.avellar.todolist.infrastructure.persistence.TaskRepository;
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
  CompleteTask completeTask(TaskGateway taskGateway) {
    return new CompleteTask(taskGateway);
  }
  @Bean
  TaskGateway taskGateway(TaskRepository repository, TaskEntityMapper mapper) {
    return new TaskRepositoryGateway(repository, mapper) {
      @Override
      public TaskPort completeTask(Long taskId) {
        return null;
      }
    };
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
