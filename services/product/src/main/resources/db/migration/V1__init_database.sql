create table if not exists category
(
    id bigint not null primary key,
    description varchar(255),
    name varchar(255)
);

create table if not exists product
(
    id bigint not null primary key,
    stock_level int not null,
    description varchar(255),
    name varchar(255),
    price numeric(38,2),
    category_id bigint constraint fkCategoryId references category
);

create sequence if not exists category_seq increment by 50;
create sequence if not exists product_seq increment by 50;