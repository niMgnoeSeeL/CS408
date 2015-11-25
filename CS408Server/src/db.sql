CREATE DATABASE able;
use able;
CREATE TABLE booth (
id INT,
PRIMARY KEY(id)
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

insert into Booth values(0);
insert into Booth values(1);
insert into Booth values(2);
insert into Booth values(3);
insert into Booth values(4);
insert into Booth values(5);

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
insert into Timelog values(0,3,'2015-12-11 11:20:00',NULL,0);
