create table if not exists category
(
    id          integer not null primary key,
    description varchar,
    name        varchar
);

create table if not exists product
(
    id                 integer not null primary key,
    name               varchar,
    description        varchar,
    available_quantity bigint  not null,
    price              numeric(38, 2),
    category_id        integer
        constraint fk_product_category references category
);

create sequence if not exists category_seq increment by 50;
create sequence if not exists product_seq increment by 50;
