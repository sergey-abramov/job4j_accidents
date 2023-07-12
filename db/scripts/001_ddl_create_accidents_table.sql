CREATE TABLE types (
  id serial primary key,
  name varchar
);

CREATE TABLE rules (
  id serial primary key,
  name varchar
);

CREATE TABLE accidents (
  id serial primary key,
  name text,
  description text,
  address varchar,
  type_id int references types(id) not null
);

CREATE TABLE rules_accidents (
  id serial primary key,
  rule_id int references rules(id) not null,
  accident_id int references accidents(id) not null
);
