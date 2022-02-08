

INSERT INTO adproject1.goal 
(id, end_date, goal_description, start_date, status, target_count, total_meal_count, author_user_id) 
VALUES
	(1, '2021-12-06', 'Eat vegetables', '2021-10-06', 'COMPLETED', 60, 60, 3),
	(2, '2022-01-06', 'Eat fruits', '2021-12-07', 'COMPLETED', 30, 30, 3),
	(3, NULL, 'Lose weight', '2022-01-07', 'IN_PROGRESS', 40, 40, 3);


INSERT INTO adproject1.meal_entry
('id', 'description', 'feeling', 'flagged', 'imageurl', 'time_stamp', 'title', 'track_score', 'visibility', 'author_user_id', 'goal_id') 
VALUES
	(1, 'happy', 'JOYFUL', 0, NULL, NULL, NULL, 1, 1, 3, 3),
	(2, 'yummy', 'JOYFUL', 0, NULL, NULL, NULL, 1, 1, 3, 3),
	(3, 'sinful', 'PENSIVE', 0, NULL, NULL, NULL, 0, 1, 3, 3),
	(4, 'simple', 'HAPPY', 0, NULL, NULL, NULL, 1, 1, 3, 3);