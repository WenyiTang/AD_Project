DROP SCHEMA IF EXISTS foodDiaryApp;
CREATE SCHEMA foodDiaryApp;
USE foodDiaryApp;

CREATE TABLE foodDiaryApp.Goal (
	goalid INTEGER NOT NULL AUTO_INCREMENT,
	goaldescription VARCHAR NULL,
	totalmealcount INTEGER NULL,
	targetcount INTEGER NULL,
	status ENUM('STARTED', 'IN_PROGRESS', 'COMPLETED', 'CANCELLED') NOT NULL,
	startdate DATE NULL,
	enddate DATE NULL,
	PRIMARY KEY (goalid)
);

CREATE TABLE foodDiaryApp.User (
	userid INTEGER NOT NULL AUTO_INCREMENT,
	PRIMARY KEY (userid)
);

CREATE TABLE foodDiaryApp.Comment (
	commentid INTEGER NOT NULL AUTO_INCREMENT,
	PRIMARY KEY (commentid)
);

CREATE TABLE foodDiaryApp.MealEntry (
	mealentryid INTEGER NOT NULL AUTO_INCREMENT,
	imageurl VARCHAR NULL,
	visibility BOOLEAN NULL,
	title VARCHAR NULL,
	description VARCHAR NULL,
	flagged BOOLEAN NULL,
	feelings ENUM(...) NULL,
	trackscore INTEGER NULL,
	timestamp DATETIME NULL,
	goalid INTEGER NOT NULL,
	likerid INTEGER NULL,
	authorid INTEGER NOT NULL,
	commentid INTEGER NULL,
	PRIMARY KEY (mealentryid),
	CONSTRAINT goalidfk
		FOREIGN KEY (goalid) REFERENCES foodDiaryApp.Goal (goalid) ON DELETE NO ACTION ON UPDATE NO ACTION,
	CONSTRAINT authoridfk
		FOREIGN KEY (authorid) REFERENCES foodDiaryApp.User (userid) ON DELETE NO ACTION ON UPDATE NO ACTION,
		
);