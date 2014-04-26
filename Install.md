

mysql> create database olid default charset utf8 default collate utf8_bin;
mysql> grant all on olid.* to 'olid'@'localhost' identified by 'olid';
mysql> grant all on olid.* to 'olid'@'localhost.localdomain' identified by 'olid';
mysql> grant all on olid.* to 'olid'@'%' identified by 'olid';
mysql> grant all on common.* to 'olid'@'localhost';
mysql> grant all on common.* to 'olid'@'localhost.localdomain';
mysql> grant all on common.* to 'olid'@'%';
