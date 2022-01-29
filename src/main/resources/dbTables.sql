DROP SCHEMA IF EXISTS foodDiaryApp;
CREATE SCHEMA foodDiaryApp;
USE foodDiaryApp;

CREATE TABLE foodDiaryApp.Goal (
	goal_id INTEGER NOT NULL AUTO_INCREMENT,
	goaldescription VARCHAR(250) NULL,
	totalmealcount INTEGER NULL,
	targetcount INTEGER NULL,
	status ENUM('STARTED', 'IN_PROGRESS', 'COMPLETED', 'CANCELLED') NOT NULL,
	startdate DATE NULL,
	enddate DATE NULL,
	PRIMARY KEY (goal_id)
);

CREATE TABLE foodDiaryApp.MealEntry (
	mealentry_id INTEGER NOT NULL AUTO_INCREMENT,
	imageurl VARCHAR(500) NULL,
	visibility BOOLEAN NULL,
	title VARCHAR(50) NULL,
	description VARCHAR(250) NULL,
	flagged BOOLEAN NULL,
	feelings ENUM('CRYING', 'PENSIVE', 'HAPPY', 'JOYFUL') NOT NULL,
	trackscore INTEGER NULL,
	timestamp DATETIME NULL,
	goalid INTEGER NOT NULL,
	authorid INTEGER NOT NULL,
	PRIMARY KEY (mealentry_id),
	CONSTRAINT goalidfk
		FOREIGN KEY (goalid) REFERENCES foodDiaryApp.Goal (goal_id) ON DELETE NO ACTION ON UPDATE NO ACTION,
	CONSTRAINT authoridfk
		FOREIGN KEY (authorid) REFERENCES foodDiaryApp.User (user_id) ON DELETE NO ACTION ON UPDATE NO ACTION
		
);