drop table if exists `Run`;
create table `Run`(
	`imei` varchar(32) not null,
	`token` varchar(32) default null,
	`userId` int(11) default null,
	`id` varchar(32) default null,
	`scores` int(11) default null,
	`coins` int(11) default null,
	`times` int(11) default null,
	`length` int(11) default null,
	`startTime` datetime default null,
	`endTime` datetime default null,
	primary key(`imei`)
)engine=InnoDB default charset=utf8;
