package com.avellar.todolist.infrastructure.controller;

public record CreateTaskResponse(
        Long id, String name, String description,
        Boolean realized, Boolean prioritized
) {

    public static CreateTaskResponseBuilder builder() {
        return new CreateTaskResponseBuilder();
    }

    public static class CreateTaskResponseBuilder {
        private Long id;
        private String name;
        private String description;
        private Boolean realized;
        private Boolean prioritized;

        private CreateTaskResponseBuilder() {
            // Construtor privado para garantir que a instância seja criada usando o método builder()
        }

        public CreateTaskResponseBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public CreateTaskResponseBuilder name(String name) {
            this.name = name;
            return this;
        }

        public CreateTaskResponseBuilder description(String description) {
            this.description = description;
            return this;
        }

        public CreateTaskResponseBuilder realized(Boolean realized) {
            this.realized = realized;
            return this;
        }

        public CreateTaskResponseBuilder prioritized(Boolean prioritized) {
            this.prioritized = prioritized;
            return this;
        }

        public CreateTaskResponse build() {
            return new CreateTaskResponse(id, name, description, realized, prioritized);
        }
    }
}
