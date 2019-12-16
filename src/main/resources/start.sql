-- roles
insert into role(role_id, role)
values (1, 'ADMIN');

insert into role(role_id, role)
values (2, 'USER');

-- users
insert into user(id, active, email, lastname, login, name, password)
values (1, 1, 'teges@mail.com', 'Nowak', 'admin', 'Stefan', '$2a$10$z7iFcwS4VpDYYb82VZVsl.11EmMqlQUpNv.Ukz4w5Cyulva5nq45q');

insert into user(id, active, email, lastname, login, name, password)
values (2, 1, 'test456@mail.com', 'Kowalski', 'user', 'Adam', '$2a$10$MQUy8M/E6O5OIeUBf0XT0eHubWFkOfa6fbQvbeReal1Q.wJyzkBKO');

-- add roles into users
insert into user_role(user_id, role_id) values (1, 1);
insert into user_role(user_id, role_id) values (1, 2);
insert into user_role(user_id, role_id) values (2, 2);