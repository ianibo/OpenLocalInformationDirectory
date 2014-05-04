
# SET foreign_key_checks = 0;
# drop database if exists olid;
# drop database if exists olidLive;
# drop database if exists common;
# SET foreign_key_checks = 1;

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


After starting the admin app, you might wish to load some test data.

a script is provided to load the IPSV subjects and a number of UK organisations - run scripts/setup.sh
