drop database if exists mqjia;
create database mqjia;
use mqjia;
drop table if exists fileInfo;
create table fileInfo(
	`id` int(11) auto_increment,
	`userId` int(11),
	`fileName` varchar(255),
	`tableName` varchar(255),
	`excelFileName` varchar(255),
	`htmlFileName` varchar(255),
	`createTime` datetime,
	primary key(`id`)
)ENGINE=INNODB,CHARSET=utf8;
drop table if exists field;
create table field(
	`id` int(11) auto_increment,
	`infoId` int(11),
	`fieldName` varchar(255),
	 primary key(`id`)
)ENGINE=INNODB,CHARSET=utf8;
drop table if exists formTracking;
create table formTracking(
	`id` int(11) auto_increment,
	`infoId` int(11),
	`dataId` int(11),
	`userId` int(11),
	`modifyTime` datetime,
	primary key(`id`)
)ENGINE=INNODB,CHARSET=utf8;
drop table if exists `user`;
create table `user`(
	`id` int(11) auto_increment,
	`nikeName` varchar(255),
	`loginName` varchar(255),
	`password` varchar(255),
	`createTime` datetime,
	primary key(`id`),
  unique(`loginName`)
)ENGINE=INNODB,CHARSET=utf8;