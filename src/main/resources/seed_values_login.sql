drop schema if exists adproject;
create database adproject;

delete from adproject.user;

insert into adproject.role
(type)
values
('USER'),
('ADMIN');

insert into adproject.user
(date_of_birth, email, enabled, gender, height, name, password, profile_pic, username, weight)
values
(str_to_date('2022-01-01', '%Y-%m-%d'), 'james@gmail.com', true,'', 160, 'James', '$2a$12$HY72DB4KJuPZpJSjAdWJ8OKvsCRlm.gvA91BXKHf.xaG/Gx602CVO', '','james', 50),
(str_to_date('2022-01-01', '%Y-%m-%d'), 'jill@gmail.com', true,'', 160, 'Jill', '$2a$12$HY72DB4KJuPZpJSjAdWJ8OKvsCRlm.gvA91BXKHf.xaG/Gx602CVO', '', 'jill', 50),
(str_to_date('2022-01-01', '%Y-%m-%d'), 'jake@gmail.com', true,'', 160, 'Jake', '$2a$12$HY72DB4KJuPZpJSjAdWJ8OKvsCRlm.gvA91BXKHf.xaG/Gx602CVO', '', 'jake', 50),
(str_to_date('2022-01-01', '%Y-%m-%d'), 'jane@gmail.com', true,'', 160, 'Jane', '$2a$12$HY72DB4KJuPZpJSjAdWJ8OKvsCRlm.gvA91BXKHf.xaG/Gx602CVO', '', 'jane', 50),
(str_to_date('2022-01-01', '%Y-%m-%d'), 'appfooddiary@gmail.com', true,'', 160, 'CY', '$2a$12$HY72DB4KJuPZpJSjAdWJ8OKvsCRlm.gvA91BXKHf.xaG/Gx602CVO', '', 'cy', 50),
(null, 'admin@gmail.com', true,'', 0, 'admin', '$2a$12$HY72DB4KJuPZpJSjAdWJ8OKvsCRlm.gvA91BXKHf.xaG/Gx602CVO', '','admin', 0);


insert into adproject.users_roles
(user_id, role_id)
values
(1,1),
(2,1),
(3,1),
(4,1),
(5,1),
(6,2);
