package com.avellar.todolist.infrastructure.persistence;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.autoconfigure.web.WebProperties;

@Entity
@Table(name = "task")

public class Task {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private @NotBlank String name;
        private @NotBlank String description;
        private @NotBlank Boolean realized;
        private @NotBlank Boolean prioritized;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getRealized() {
        return realized;
    }

    public void setRealized(Boolean realized) {
        this.realized = realized;
    }

    public Boolean getPrioritized() {
        return prioritized;
    }

    public void setPrioritized(Boolean prioritized) {
        this.prioritized = prioritized;
    }

    public Task(Long id, String name, String description, Boolean realized, Boolean prioritized) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.realized = realized;
        this.prioritized = prioritized;
    }

}