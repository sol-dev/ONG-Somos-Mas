INSERT INTO roles(name, description, created_time, update_time) VALUES("ROLE_ADMIN","rol administrador", now(), now());
INSERT INTO roles(name, description, created_time, update_time) VALUES("ROLE_USER", "rol usurio", now(), now());

insert into news (id,content,image,name,deleted) values (1,"esto es la prueba 1 de contenido","","prueba1",false);
insert into news (id,content,image,name,deleted) values (2,"esto es la prueba 2 de contenido","","prueba2",false);
insert into news (id,content,image,name,deleted) values (3,"esto es la prueba 3 de contenido","","prueba3",false);

insert into users (id,email,first_name,last_name,role_id,deleted,password) values (1, "mail_1@mail.com","nombre1","apellido1",1,false,"12345");
insert into users (id,email,first_name,last_name,role_id,deleted,password) values (2, "mail_2@mail.com","nombre2","apellido2",1,false,"67890");
insert into users (id,email,first_name,last_name,role_id,deleted,password) values (3, "mail_3@mail.com","nombre3","apellido3",2,false,"13579");
