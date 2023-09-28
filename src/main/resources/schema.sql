CREATE TABLE IF NOT EXISTS task (
  id bigint AUTO_INCREMENT primary key,
    name varchar(255) not null,
    description varchar(255) not null,
    prioritized boolean not null,
    realized boolean not null,
    created_at timestamp not null,
    updated_at timestamp not null
);