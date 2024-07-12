CREATE TABLE parents(
    id BIGSERIAL primary key,
    name varchar(255)
);

CREATE TABLE groups(
    id BIGSERIAL primary key,
    parent_id BIGINT not null references parents(id)
);

CREATE TABLE children(
    id BIGSERIAL primary key,
    parent_id BIGINT NOT NULL references parents(id),
    group_id BIGINT references groups(id)
);
