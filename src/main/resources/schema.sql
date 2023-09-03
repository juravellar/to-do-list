CREATE TABLE IF NOT EXISTS place (
  id bigint AUTO_INCREMENT primary key,
  name varchar(255) not null,
  description varchar(255) not null,
  done bit not null,
  preference bit not null
);