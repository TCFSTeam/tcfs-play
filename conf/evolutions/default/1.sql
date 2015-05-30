
create table menu_item (
  id                        number(10) not null,
  item_price                number(19,4),
  is_deleted                number(1),
  item_description          varchar2(255),
  constraint pk_menu_item primary key (id))
;

create table order_item (
  id                        number(10) not null,
  menu_item_id              number(10),
  is_ready                  number(1),
  is_returned               number(1),
  constraint pk_order_item primary key (id))
;

create table order_tcfs (
  id                        number(10) not null,
  guests_count              number(10),
  waiter                    varchar2(255),
  order_status              varchar2(255),
  table_id                  number(10),
  saved                     number(1),
  created_at                timestamp,
  closed_at                 timestamp,
  constraint pk_order_tcfs primary key (id))
;

create table reservation (
  id                        number(10) not null,
  table_id                  number(10),
  is_active                 number(1),
  reservator                varchar2(255),
  created_at                timestamp,
  start_at                  timestamp,
  constraint pk_reservation primary key (id))
;

create table table_tcfs (
  id                        number(10) not null,
  is_active                 number(1),
  constraint pk_table_tcfs primary key (id))
;

create table user_tcfs (
  email                     varchar2(255) not null,
  name                      varchar2(255),
  password                  varchar2(255),
  image_path                varchar2(255),
  member_type               varchar2(4),
  constraint ck_user_tcfs_member_type check (member_type in ('CK','WT','CASH','ADM')),
  constraint pk_user_tcfs primary key (email))
;


create table order_tcfs_order_item (
  order_tcfs_id                  number(10) not null,
  order_item_id                  number(10) not null,
  constraint pk_order_tcfs_order_item primary key (order_tcfs_id, order_item_id))
;

create table table_tcfs_reservation (
  table_tcfs_id                  number(10) not null,
  reservation_id                 number(10) not null,
  constraint pk_table_tcfs_reservation primary key (table_tcfs_id, reservation_id))
;
create sequence menu_item_seq;

create sequence order_item_seq;

create sequence order_tcfs_seq;

create sequence reservation_seq;

create sequence table_tcfs_seq;

create sequence user_tcfs_seq;




alter table order_tcfs_order_item add constraint fk_order_tcfs_order_item_or_01 foreign key (order_tcfs_id) references order_tcfs (id);

alter table order_tcfs_order_item add constraint fk_order_tcfs_order_item_or_02 foreign key (order_item_id) references order_item (id);

alter table table_tcfs_reservation add constraint fk_table_tcfs_reservation_t_01 foreign key (table_tcfs_id) references table_tcfs (id);

alter table table_tcfs_reservation add constraint fk_table_tcfs_reservation_r_02 foreign key (reservation_id) references reservation (id);


drop table menu_item cascade constraints purge;

drop table order_item cascade constraints purge;

drop table order_tcfs cascade constraints purge;

drop table order_tcfs_order_item cascade constraints purge;

drop table reservation cascade constraints purge;

drop table table_tcfs cascade constraints purge;

drop table table_tcfs_reservation cascade constraints purge;

drop table user_tcfs cascade constraints purge;

drop sequence menu_item_seq;

drop sequence order_item_seq;

drop sequence order_tcfs_seq;

drop sequence reservation_seq;

drop sequence table_tcfs_seq;

drop sequence user_tcfs_seq;

