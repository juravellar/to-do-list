package com.avellar.todolist.domain.entity;

public record Task (Long id, String name, String description, Boolean realized, Boolean prioritized) {

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Long getId() {
        return id;
    }
    public Boolean getPrioritized() {
        return prioritized;
    }

    public Boolean getRealized() {
        return realized;
    }
}
