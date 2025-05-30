create table if not exists workspaces
(
    id         bigint generated by default as identity
        primary key,
    name       varchar(255),
    created_at timestamp(6)
);

alter table workspaces
    owner to core;