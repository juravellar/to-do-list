package com.avellar.todolist.main;

import com.avellar.todolist.application.gateways.TaskGateway;
import com.avellar.todolist.application.usecases.CreateTaskInteractor;
import com.avellar.todolist.infrastructure.controller.TaskDTOMapper;
import com.avellar.todolist.infrastructure.gateways.TaskEntityMapper;
import com.avellar.todolist.infrastructure.gateways.TaskRepositoryGateway;
import com.avellar.todolist.infrastructure.persistence.TaskRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TaskConfig {
    @Bean
    CreateTaskInteractor createTaskCase (TaskGateway taskGateway){
        return new CreateTaskInteractor(taskGateway);
    }

    @Bean
    TaskGateway taskGateway (TaskRepository taskRepository, TaskEntityMapper taskEntityMapper){
        return new TaskRepositoryGateway(taskRepository, taskEntityMapper);
    }

    @Bean
    TaskEntityMapper taskEntityMapper(){
        return new TaskEntityMapper();
    }

    @Bean
    TaskDTOMapper taskDTOMapper(){
        return new TaskDTOMapper();
    }

}
