INSERT INTO roles(name, description, created_time, update_time) VALUES("ROLE_ADMIN","rol administrador", now(), now());
INSERT INTO roles(name, description, created_time, update_time) VALUES("ROLE_USER", "rol usurio", now(), now());

insert into news (id,content,image,name,deleted) values (1,"esto es la prueba 1 de contenido","","prueba1",false);
insert into news (id,content,image,name,deleted) values (2,"esto es la prueba 2 de contenido","","prueba2",false);
insert into news (id,content,image,name,deleted) values (3,"esto es la prueba 3 de contenido","","prueba3",false);

insert into users (id,email,username,first_name,last_name,role_id,deleted,password) values (1, "mail_1@mail.com","user1","nombre1","apellido1",2,false,"$2y$12$CNlNoixKK/ISlUWdl7mlPO8PVROcBcFo12U1yL0ezgy0KM7qWJamy");
insert into users (id,email,username,first_name,last_name,role_id,deleted,password) values (2, "mail_2@mail.com","user2","nombre2","apellido2",1,false,"$2y$12$A4Dn01KTW6MKW2T5/V3T4.dlZ4wh9w1dX1f0dUqg3kBIExQ7nLW2y");
insert into users (id,email,username,first_name,last_name,role_id,deleted,password) values (3, "mail_3@mail.com","admin","nombre3","apellido3",1,false,"$2y$12$rTrjIhjdaWGsaaGXBCUXWu.cF1V2g56W1myQ0bDQfybb52baSoG1C");