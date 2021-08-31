SET FOREIGN_KEY_CHECKS=0;
delete from user_roles;
delete from users;

insert into users(id, active, password, username) values (1, true, '$2a$08$NdQKqQlUWA8yuHVBD6oWhu/Dbjpj7pHGGFzGPZjQDksCQdKWYLD4q', 'admin');
insert into users(id, active, password, username) values (2, true, '$2a$08$NdQKqQlUWA8yuHVBD6oWhu/Dbjpj7pHGGFzGPZjQDksCQdKWYLD4q', 'evgeny');

insert into user_roles(user_id,roles) values (1,'ADMIN'), (1,'USER');
insert into user_roles(user_id,roles) values (2,'USER');

