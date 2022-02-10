
insert into adproject.role
(type)
values
('USER'),
('ADMIN');

insert into adproject.user
(date_of_birth, email, enabled, gender, height, name, password, profile_pic, username, weight)
values
    (str_to_date('2022-01-01', '%Y-%m-%d'), 'james@gmail.com', true,'', 160, 'James', '$2a$12$HY72DB4KJuPZpJSjAdWJ8OKvsCRlm.gvA91BXKHf.xaG/Gx602CVO', 'james.jpeg','james', 50),
    (str_to_date('2022-01-01', '%Y-%m-%d'), 'bondjames@gmail.com', true,'', 160, 'James Bond', '$2a$12$HY72DB4KJuPZpJSjAdWJ8OKvsCRlm.gvA91BXKHf.xaG/Gx602CVO', 'bond_james.jpeg','bond_james', 50),
    (str_to_date('2022-01-01', '%Y-%m-%d'), 'jill@gmail.com', true,'', 160, 'Jill', '$2a$12$HY72DB4KJuPZpJSjAdWJ8OKvsCRlm.gvA91BXKHf.xaG/Gx602CVO', 'jill.jpeg', 'jill', 50),
    (str_to_date('2022-01-01', '%Y-%m-%d'), 'jake@gmail.com', true,'', 160, 'Jake', '$2a$12$HY72DB4KJuPZpJSjAdWJ8OKvsCRlm.gvA91BXKHf.xaG/Gx602CVO', 'jake.jpeg', 'jake', 50),
    (str_to_date('2022-01-01', '%Y-%m-%d'), 'jay@gmail.com', true,'', 160, 'Jay', '$2a$12$HY72DB4KJuPZpJSjAdWJ8OKvsCRlm.gvA91BXKHf.xaG/Gx602CVO', 'jay.jpeg', 'jay', 50),
    (str_to_date('2022-01-01', '%Y-%m-%d'), 'jane@gmail.com', true,'', 160, 'Jane', '$2a$12$HY72DB4KJuPZpJSjAdWJ8OKvsCRlm.gvA91BXKHf.xaG/Gx602CVO', 'jane.jpeg', 'jane', 50),
    (str_to_date('2022-01-01', '%Y-%m-%d'), 'jane123@gmail.com', true,'', 160, 'Jane Porter', '$2a$12$HY72DB4KJuPZpJSjAdWJ8OKvsCRlm.gvA91BXKHf.xaG/Gx602CVO', 'jane123.jpeg', 'jane123', 50),
    (str_to_date('2022-01-01', '%Y-%m-%d'), 'appfooddiary@gmail.com', true,'', 160, 'John', '$2a$12$HY72DB4KJuPZpJSjAdWJ8OKvsCRlm.gvA91BXKHf.xaG/Gx602CVO', 'john.jpeg', 'john', 50),
    (null, 'admin@gmail.com', true,'', 0, 'admin', '$2a$12$HY72DB4KJuPZpJSjAdWJ8OKvsCRlm.gvA91BXKHf.xaG/Gx602CVO', '','admin', 0);


insert into adproject.users_roles
(user_id, role_id)
values
(1,1),
(2,1),
(3,1),
(4,1),
(5,1),
(6,1),
(7,1),
(8,1),
(9,1);




INSERT INTO adproject.friend_request
(sender_user_id, recipient_user_id, status)
VALUES
    (3, 1, 'ACCEPTED'),
    (3, 2, 'ACCEPTED'),
    (3, 4, 'ACCEPTED'),
    (3, 5, 'ACCEPTED'),
    (3, 6, 'ACCEPTED'),
    (3, 7, 'ACCEPTED'),
    (3, 8, 'ACCEPTED'),
    (3, 9, 'ACCEPTED');

