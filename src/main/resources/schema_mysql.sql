drop table menu;
drop table member;
drop table order_list;
drop table order_detail;

create table menu(
    id bigint auto_increment,
    name varchar(40) not null,
    menu_code int not null,
    price decimal,
    total_order bigint default 0,
    primary key (id)
);

create table member(
    id varchar(40),
    name varchar(40) not null,
    point decimal default 0,
    primary key (id)
);

create table order_list(
    id bigint auto_increment,
    member_id varchar(40) not null,
    menu_count int,
    total_price decimal,
    primary key (id)
);

create table order_detail(
    id bigint auto_increment,
    order_list_id bigint not null,
    member_id varchar(40) not null,
    menu_id bigint not null,
    menu_count int,
    price decimal,
    primary key (id)
);