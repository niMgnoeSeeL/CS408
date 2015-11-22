CREATE DATABASE able;

CREATE TABLE booth (
id INT,
name VARCHAR(10) NOT NULL,
loc VARCHAR(5) UNIQUE NOT NULL,
description VARCHAR(140),
PRIMARY KEY(id)
);

CREATE TABLE event (
id INT,
title VARCHAR(10) NOT NULL,
booth INT,
summary VARCHAR(140),
loc VARCHAR(5),
time DATETIME,
duration INT,
PRIMARY KEY(id),
FOREIGN KEY (booth) REFERENCES booth(id)
);

CREATE TABLE timelog (
userID INT NOT NULL,
booth INT,
startTime DATETIME NOT NULL,
endTime DATETIME,
liked INT DEFAULT 0,
PRIMARY KEY(userID,booth,startTime),
FOREIGN KEY (booth) REFERENCES booth(id)
);

insert into Booth values(0, 'Google','0,0','Here is the description of Googles booth');
insert into Booth values(1, 'Samsung','0,1','Samsung has an even longer description that goes on an on forever');
insert into Booth values(2, 'LG','1,0',NULL);
insert into Booth values(3, 'Naver','1,1','Just Naver');
insert into Booth values(4, 'Daum','2,0','A  shorth description');
insert into Booth values(5, 'Kakao','2,1','Kakao Talk');

insert into Event values(0,"Seminar",0,"This is a seminar about search engines","0,1",'2015-12-11 10:00:00',75);
insert into Event values(1,"Showcase",0,"This is a showcase of all new products from Google this year","0,0",'2015-12-11 10:30:00',30);
insert into Event values(2,"Debate",1,"Samsung is hosting an open debate about the topic Ethics in CS","{0,0}",'2015-12-11 12:30:00',60);
insert into Event values(3,"Sampling",2,"LG is sampling headsets at their booth","1,0",'2015-12-11 12:30:00',10);
insert into Event values(4,"Lecture",2,"LG is hosting an lecture about software architecture","0,0",'2015-12-11 14:00:00',120);
insert into Event values(5,"Workshop",1,"At this workshop you can try out 3D modelling with the help of LG modelling tools","0,0",'2015-12-12 09:45:00',150);
insert into Event values(6,"Performance",5,"The Kakao characters are having a performance","0,1",'2015-12-12 10:10:00',25);
insert into Event values(7,"Contest",0,"Participate in the contest by submitting you most innovative ideas","1,1",'2015-12-12 10:30:00',15);
insert into Event values(8,"Speech",4,"An Daum developer talks about navigation systems","2,0",'2015-12-12 10:50:00',60);

insert into Timelog values(0,0,'2015-12-11 10:00:00','2015-12-11 10:03:00',1);
insert into Timelog values(0,1,'2015-12-11 10:04:00','2015-12-11 10:12:00',0);
insert into Timelog values(0,2,'2015-12-11 10:20:00','2015-12-11 10:25:00',1);
insert into Timelog values(0,5,'2015-12-11 10:25:00','2015-12-11 10:26:00',0);
insert into Timelog values(1,1,'2015-12-11 09:30:00','2015-12-11 10:00:00',-1);
insert into Timelog values(1,2,'2015-12-11 10:10:00','2015-12-11 11:45:00',1);
insert into Timelog values(1,3,'2015-12-11 11:50:00','2015-12-11 14:30:00',0);
insert into Timelog values(1,4,'2015-12-11 14:50:00','2015-12-11 15:00:00',1);
insert into Timelog values(2,0,'2015-12-11 08:40:00','2015-12-11 08:55:00',-1);
insert into Timelog values(2,2,'2015-12-11 09:00:00','2015-12-11 09:15:00',1);
insert into Timelog values(2,4,'2015-12-11 10:40:00','2015-12-11 11:00:00',0);
insert into Timelog values(0,2,'2015-12-11 11:00:00','2015-12-11 11:07:00',0);
insert into Timelog values(0,2,'2015-12-11 11:10:00','2015-12-11 11:20:00',0);
insert into Timelog values(1,5,'2015-12-11 15:10:00',NULL,-1);
insert into Timelog values(2,1,'2015-12-11 11:30:00','2015-12-11 11:35:00',0);
insert into Timelog values(2,5,'2015-12-11 11:45:00',NULL,1);
insert into Timelog values(0,3,'2015-12-11 11:20:00',NULL,0);


--path history
--SELECT startTime, booth FROM timelog WHERE userID = user ORDER BY startTime

--booth info
--SELECT name, description FROM booth WHERE id = id

--popular booths
--SELECT Booth.name FROM booth, timelog WHERE Booth.id = timelog.booth GROUP BY  booth.id ORDER BY COUNT(*) DESC LIMIT 3
--SELECT Booth.name FROM booth, timelog WHERE Booth.id = timelog.booth GROUP BY  booth.id ORDER BY SUM(TIMESTAMPDIFF(MINUTE,timelog.startTime,timelog.endTime)) DESC LIMIT 3

--Booth pref for one user
--SELECT booth.name,SUM(TIMESTAMPDIFF(MINUTE,timelog.startTime,timelog.endTime)) FROM booth, timelog WHERE booth.id = timelog.booth AND timelog.userID = 0 GROUP BY  booth.id ORDER BY SUM(TIMESTAMPDIFF(MINUTE,timelog.startTime,timelog.endTime)) DESC


--recommended booths

--all events
