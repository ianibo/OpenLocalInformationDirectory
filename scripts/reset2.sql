SET foreign_key_checks = 0;
drop database if exists common;
drop database if exists olid;
drop database if exists olidLive;
SET foreign_key_checks = 1;

create database olid default charset utf8 default collate utf8_bin;
create database olidLive default charset utf8 default collate utf8_bin;
create database common default charset utf8 default collate utf8_bin;

grant all on olidLive.* to 'olid'@'localhost' identified by 'olid';
grant all on olidLive.* to 'olid'@'localhost.localdomain' identified by 'olid';
grant all on olidLive.* to 'olid'@'%' identified by 'olid';
grant all on olid.* to 'olid'@'localhost' identified by 'olid';
grant all on olid.* to 'olid'@'localhost.localdomain' identified by 'olid';
grant all on olid.* to 'olid'@'%' identified by 'olid';
grant all on common.* to 'olid'@'localhost';
grant all on common.* to 'olid'@'localhost.localdomain';
grant all on common.* to 'olid'@'%';

