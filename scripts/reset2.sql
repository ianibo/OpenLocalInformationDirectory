SET foreign_key_checks = 0;
drop database common;
drop database olid;
drop database if exists olidLive;
create database common default charset utf8 default collate utf8_bin;
create database olid default charset utf8 default collate utf8_bin;
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

