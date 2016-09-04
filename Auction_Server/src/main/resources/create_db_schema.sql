/********************************************************************
 Create default database schemsa for auction porject
 

********************************************************************/

create database if not exists auction_db  CHARACTER SET utf8 COLLATE utf8_bin;

create user if not exists 'auction_user'@'%' IDENTIFIED BY 'auction_user_pw';
GRANT ALL PRIVILEGES ON auction_db.* TO 'auction_user'@'%';
FLUSH PRIVILEGES;
create user if not exists 'auction_user'@'localhost' IDENTIFIED BY 'auction_user_pw';
GRANT ALL PRIVILEGES ON auction_db.* TO 'auction_user'@'localhost';
FLUSH PRIVILEGES;


use auction_db;

create table users(
user_id			int NOT NULL AUTO_INCREMENT,			
user_name		varchar(100) NOT NULL,
user_pwd		varchar(100),
first_name		varchar(100),
last_name		varchar(100),
phone_number	varchar(100),
email			varchar(300),
last_login_time timestamp(6) default current_timestamp(6) on update current_timestamp(6),
insert_time 	timestamp(6) default current_timestamp(6),
update_time 	timestamp(6) default current_timestamp(6) on update current_timestamp(6),
primary key (user_id),
key user_name (user_name),
key insert_time (insert_time)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin
;


create table item_categories(
item_category_id	int NOT NULL AUTO_INCREMENT,			
item_category_name 	varchar(1000) NOT NULL,
insert_time 	timestamp(6) default current_timestamp(6),
update_time 	timestamp(6) default current_timestamp(6) on update current_timestamp(6),
primary key (item_category_id),
key item_category_name (item_category_name),
key insert_time (insert_time)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin
;


create table items(
item_id				int NOT NULL AUTO_INCREMENT,			
item_user_id		int NOT NULL,
item_category		int NOT NULL,
item_name			varchar(100),
item_desc			varchar(1000),
item_picture		blob,
item_start_price	bigint,
item_last_bid_price	bigint,
item_last_bid_time 	timestamp(6),
item_last_bid_userid int ,
insert_time 	timestamp(6) default current_timestamp(6),
update_time 	timestamp(6) default current_timestamp(6) on update current_timestamp(6),
primary key (item_id),
FOREIGN KEY `fkCategory` (`item_category`) REFERENCES `item_categories` (`item_category_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
FOREIGN KEY `fkUserid` (`item_user_id`) REFERENCES `users` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
key item_category (item_category),
key item_last_bid_price (item_last_bid_price),
key item_last_bid_time (item_last_bid_time),
key item_last_bid_userid (item_last_bid_userid),
key insert_time (insert_time)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin
;


create table auction_bid_transactions(
auc_bid_trx_id		int NOT NULL AUTO_INCREMENT,
user_id				int NOT NULL,
item_id				int NOT NULL,
item_bid_time 	timestamp(6),
item_bid_price	bigint,
insert_time 	timestamp(6) default current_timestamp(6),
update_time 	timestamp(6) default current_timestamp(6) on update current_timestamp(6),
primary key (auc_bid_trx_id),
FOREIGN KEY `fkUser` (`user_id`) REFERENCES `users` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
FOREIGN KEY `fkItem` (`item_id`) REFERENCES `items` (`item_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
key unique_key(user_id,item_id,item_bid_time,item_bid_price),
key item_id (item_id),
key item_bid_time (item_bid_time),
key item_bid_price (item_bid_price),
key insert_time (insert_time)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin
;

-- insert new data
insert into users(user_id,user_name,user_pwd,first_name,last_name)
	values (0,"Admin","Admin","Administrator","Administrator");
