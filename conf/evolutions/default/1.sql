# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table order_it (
  id                        integer not null,
  waiter                    varchar(255),
  order_status              varchar(255),
  table                     integer,
  constraint pk_order_it primary key (id))
;

create table order_item (
  id                        integer not null,
  item_price                double,
  item_name                 varchar(255),
  item_description          varchar(255),
  constraint pk_order_item primary key (id))
;

create table user (
  email                     varchar(255) not null,
  name                      varchar(255),
  password                  varchar(255),
  constraint pk_user primary key (email))
;

create sequence order_it_seq;

create sequence order_item_seq;

create sequence user_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists order_it;

drop table if exists order_item;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists order_it_seq;

drop sequence if exists order_item_seq;

drop sequence if exists user_seq;

