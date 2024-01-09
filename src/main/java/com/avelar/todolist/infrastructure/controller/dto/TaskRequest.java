package com.avelar.todolist.infrastructure.controller.dto;


public record TaskRequest(
   String name, String description, Boolean prioritized, Boolean realized) {

}
