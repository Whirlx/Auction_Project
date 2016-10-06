/********************************************************************
 Create default database schemsa for auction porject
 

********************************************************************/

drop database if exists auction_db;
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
primary key (user_id),
unique key user_name (user_name),
key insert_time (insert_time)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin
;


create table item_categories(
item_category_id	int NOT NULL AUTO_INCREMENT,			
item_category_name 	varchar(100) NOT NULL,
insert_time 	timestamp(6) default current_timestamp(6),
primary key (item_category_id),
unique key item_category_name (item_category_name),
key insert_time (insert_time)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin
;


create table items(
item_id				      int NOT NULL AUTO_INCREMENT,			
item_user_id		      int NOT NULL,
item_user_name            varchar(100) NOT NULL,
item_category		      varchar(100) NOT NULL on delete default Default,
item_name			      varchar(100),
item_desc			      varchar(1000),
item_picture              LONGTEXT,
item_num_bids             int default 0,
item_start_price	      bigint,
item_latest_bid_price	  bigint,
item_latest_bid_time 	  timestamp(6) on update current_timestamp(6),
item_latest_bid_userid    int,
item_latest_bid_username  varchar(100),
auction_start_time 	      timestamp(6),
duration_in_minutes       int default 60,
isAuctionOver             boolean default false,
primary key (item_id),
FOREIGN KEY `fkCategory` (`item_category`) REFERENCES `item_categories` (`item_category_name`) ON DELETE NO ACTION ON UPDATE NO ACTION,
FOREIGN KEY `fkUserid` (`item_user_id`) REFERENCES `users` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
key item_category (item_category),
unique key item_name  (item_name),
key item_latest_bid_price (item_latest_bid_price),
key item_latest_bid_time (item_latest_bid_time),
key item_latest_bid_userid (item_latest_bid_userid),
key auction_start_time (auction_start_time)
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
SET session SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
insert into users(user_id,user_name,user_pwd,first_name,last_name,phone_number,email)
	values (0,"Admin","Admin","Administrator","Administrator", "-", "-");

insert into item_categories(item_category_id,item_category_name)
	values (0,"Default");
