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
    (null, 'admin@gmail.com', true,'', 0, 'admin', '$2a$12$HY72DB4KJuPZpJSjAdWJ8OKvsCRlm.gvA91BXKHf.xaG/Gx602CVO', '','admin', 0),
    (null, 'admin2@gmail.com', true,'', 0, 'admin2', '$2a$12$HY72DB4KJuPZpJSjAdWJ8OKvsCRlm.gvA91BXKHf.xaG/Gx602CVO', '','admin2', 0);


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
(9,2),
(10,2);


INSERT INTO adproject.goal
(id, end_date, goal_description, start_date, status, target_count, total_meal_count)
VALUES
    (1, '2022-02-07', 'Eat very healthy', '2022-02-05', 'STARTED', 3, 12),
    (2, '2022-02-10', 'Eat healthy', '2022-02-05', 'IN_PROGRESS', 4, 20),
    (3, '2022-02-10', 'No sweet drinks', '2022-02-05', 'STARTED', 20, 30);

INSERT INTO `user_goals` (`user_user_id`, `goals_id`, `goals_order`) VALUES (1, 1, 0);
INSERT INTO `user_goals` (`user_user_id`, `goals_id`, `goals_order`) VALUES (2, 2, 0);
INSERT INTO `user_goals` (`user_user_id`, `goals_id`, `goals_order`) VALUES (2, 3, 1);

INSERT INTO adproject.meal_entry
(`id`, `description`, `feeling`, `flagged`, `imageurl`, `time_stamp`, `title`, `track_score`, `visibility`, `author_user_id`, `goal_id`)
VALUES
	(1, 'happy', 'JOY', 0, NULL, NULL, NULL, 1, 1, 3, 3),
	(2, 'yummy', 'JOY', 0, NULL, NULL, NULL, 1, 1, 3, 3),
	(3, 'sinful', 'PENSIVE', 0, NULL, NULL, NULL, 0, 1, 3, 3),
	(4, 'simple', 'HAPPY', 0, NULL, NULL, NULL, 1, 1, 3, 3);

INSERT INTO adproject.friend_request
(sender_user_id, recipient_user_id, status)
VALUES
    (3, 1, 'ACCEPTED'),
    (3, 2, 'ACCEPTED'),
    (3, 6, 'ACCEPTED'),
    (3, 7, 'PENDING'),
    (5, 3, 'PENDING');

INSERT INTO `meal_entry` (`id`, `description`, `feeling`, `flagged`, `imageurl`, `time_stamp`, `title`, `track_score`, `visibility`, `author_user_id`, `goal_id`) VALUES (5, 'best chicken rice in town', 'JOY', b'0', 'https://static.thehoneycombers.com/wp-content/uploads/sites/2/2020/06/chicken-rice.jpg', '2022-02-08 14:21:32.000000', 'chicken rice', 0, b'0', 1, NULL);
INSERT INTO `meal_entry` (`id`, `description`, `feeling`, `flagged`, `imageurl`, `time_stamp`, `title`, `track_score`, `visibility`, `author_user_id`, `goal_id`) VALUES (6, 'duck rice desc', 'HAPPY', b'0', 'https://eatbook.sg/wp-content/uploads/2018/02/braised-duck-rice-wei-ji.jpg', '2022-02-08 14:21:32.000000', 'duck rice', 0, b'0', 1, NULL);

INSERT INTO `report` (`id`, `comments`, `date_reported`, `date_resolved`, `reason`, `status`, `meal_entry_id`, `reporter_user_id`, `resolved_by_user_id`) VALUES (1, NULL, '2022-02-07', NULL, 'language', 'PENDING', 5, 2, NULL);
INSERT INTO `report` (`id`, `comments`, `date_reported`, `date_resolved`, `reason`, `status`, `meal_entry_id`, `reporter_user_id`, `resolved_by_user_id`) VALUES (2, NULL, '2022-02-08', NULL, 'picture', 'PENDING', 6, 4, NULL);
INSERT INTO `report` (`id`, `comments`, `date_reported`, `date_resolved`, `reason`, `status`, `meal_entry_id`, `reporter_user_id`, `resolved_by_user_id`) VALUES (3, NULL, '2022-02-10', NULL, 'language', 'PENDING', 5, 3, NULL);
INSERT INTO `report` (`id`, `comments`, `date_reported`, `date_resolved`, `reason`, `status`, `meal_entry_id`, `reporter_user_id`, `resolved_by_user_id`) VALUES (4, NULL, '2022-02-08', NULL, 'picture', 'PENDING', 5, 4, NULL);

