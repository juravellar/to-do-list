package com.avellar.todolist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.avellar.todolist")
public class TaskListApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(TaskListApiApplication.class, args);
  }

}
