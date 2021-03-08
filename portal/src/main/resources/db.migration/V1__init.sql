drop table sbp.company_product;

drop table sbp.company;

create table sbp.company
(
    id             uuid         not null
        constraint company_pkey
            primary key,
    created_date   timestamp,
    modified_date  timestamp,
    active         integer      not null,
    admin_email    varchar(255) not null,
    admin_name     varchar(255) not null,
    company_name   varchar(255) not null
        constraint company_key_name
            unique,
    deleted        integer      not null,
    logo           oid,
    memo           varchar(255),
    service_url    varchar(255),
    tel            varchar(255),
    trial_end_date timestamp,
    users_limit    integer
        constraint company_users_limit_check
            check (users_limit >= 1)
);


create table sbp.verification
(
    id            bigint       not null
        constraint verification_pkey
            primary key,
    created_date  timestamp,
    modified_date timestamp,
    code          varchar(255),
    email_from    varchar(255),
    email_to      varchar(255) not null,
    type          integer      not null,
    constraint verification_key1
        unique (email_to, type)
);

create table sbp.product
(
    id   bigint not null
        constraint product_pkey
            primary key,
    name varchar(255)
);

create table sbp.company_product
(
    id         bigserial not null
        constraint company_product_pkey
            primary key,
    company_id uuid
        constraint company_product_fk1
            references sbp.company,
    product_id bigint
        constraint company_product_fk2
            references sbp.product
);


create sequence sbp.seq
    increment by 50;

create sequence sbp.hibernate_sequence;

create sequence sbp.company_product_id_seq;

alter table sbp.company_product
    owner to sbpapp;

alter table sbp.product
    owner to sbpapp;

alter table sbp.company
    owner to sbpapp;

alter table sbp.verification
    owner to sbpapp;

alter sequence sbp.seq owner to sbpapp;

alter sequence sbp.company_product_id_seq owner to sbpapp;

alter sequence sbp.hibernate_sequence owner to sbpapp;

