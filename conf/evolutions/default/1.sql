# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table menu_item (
  id                        integer not null,
  item_price                double,
  is_deleted                boolean,
  item_description          varchar(255),
  constraint pk_menu_item primary key (id))
;

create table order_item (
  id                        integer not null,
  menu_item_id              integer,
  is_ready                  boolean,
  is_returned               boolean,
  constraint pk_order_item primary key (id))
;

create table order_tcfs (
  id                        integer not null,
  guests_count              integer,
  waiter                    varchar(255),
  order_status              varchar(255),
  table                     integer,
  saved                     boolean,
  created_at                timestamp,
  closed_at                 timestamp,
  constraint pk_order_tcfs primary key (id))
;

create table reservation (
  id                        integer not null,
  reservation_id            integer,
  created_at                timestamp,
  start_at                  timestamp,
  constraint pk_reservation primary key (id))
;

create table table (
  id                        integer not null,
  table_id                  integer,
  constraint pk_table primary key (id))
;

create table user (
  email                     varchar(255) not null,
  name                      varchar(255),
  password                  varchar(255),
  image_path                varchar(255),
  member_type               varchar(4),
  constraint ck_user_member_type check (member_type in ('CK','WT','CASH','ADM')),
  constraint pk_user primary key (email))
;


create table order_tcfs_order_item (
  order_tcfs_id                  integer not null,
  order_item_id                  integer not null,
  constraint pk_order_tcfs_order_item primary key (order_tcfs_id, order_item_id))
;

create table table_reservation (
  table_id                       integer not null,
  reservation_id                 integer not null,
  constraint pk_table_reservation primary key (table_id, reservation_id))
;
create sequence menu_item_seq;

create sequence order_item_seq;

create sequence order_tcfs_seq;

create sequence reservation_seq;

create sequence table_seq;

create sequence user_seq;




alter table order_tcfs_order_item add constraint fk_order_tcfs_order_item_orde_01 foreign key (order_tcfs_id) references order_tcfs (id) on delete restrict on update restrict;

alter table order_tcfs_order_item add constraint fk_order_tcfs_order_item_orde_02 foreign key (order_item_id) references order_item (id) on delete restrict on update restrict;

alter table table_reservation add constraint fk_table_reservation_table_01 foreign key (table_id) references table (id) on delete restrict on update restrict;

alter table table_reservation add constraint fk_table_reservation_reservat_02 foreign key (reservation_id) references reservation (id) on delete restrict on update restrict;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists menu_item;

drop table if exists order_item;

drop table if exists order_tcfs;

drop table if exists order_tcfs_order_item;

drop table if exists reservation;

drop table if exists table;

drop table if exists table_reservation;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists menu_item_seq;

drop sequence if exists order_item_seq;

drop sequence if exists order_tcfs_seq;

drop sequence if exists reservation_seq;

drop sequence if exists table_seq;

drop sequence if exists user_seq;

