create sequence hibernate_sequence;

CREATE TABLE common_revision_entity
(
    id           integer NOT NULL
        CONSTRAINT common_revision_entity_pkey PRIMARY KEY,
    "timestamp"  bigint  NOT NULL,
    triggered_by character varying(255)
);

CREATE SEQUENCE posts_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE TABLE posts
(
    id           bigint       not null DEFAULT nextval('posts_id_seq'::regclass),
    title        varchar(255) not null,
    content      text         NOT NULL,
    video_link   varchar(255) not null,

    created_by   varchar(255) not null,
    created_date timestamp    not null,
    updated_by   varchar(255) not null,
    updated_date timestamp    not null,
    version      bigint,

    CONSTRAINT posts_pkey PRIMARY KEY (id)
);

CREATE TABLE posts_aud
(
    id           bigint  not null,

    title        varchar(255),
    content      text,
    video_link   varchar(255),

    created_by   varchar(255),
    created_date timestamp,
    updated_by   varchar(255),
    updated_date timestamp,
    version      bigint,
    rev          integer NOT NULL,
    revtype      smallint,
    CONSTRAINT posts_aud_pkey PRIMARY KEY (id, rev),
    CONSTRAINT posts_aud_2_revision FOREIGN KEY (rev)
        REFERENCES common_revision_entity (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

create table post_documents
(
    id           bigserial    not null,
    post_id      bigint       not null,
    folder       varchar(100) not null,
    file_name    varchar(200) not null,
    created_by   varchar(255) not null,
    created_date timestamp    not null,
    updated_by   varchar(255) not null,
    updated_date timestamp    not null,
    version      int8,
    primary key (id),
    constraint post_documents_2_posts
        foreign key (post_id)
            references posts (id)
                match simple
            on update no action
            on delete no action
);

create table post_documents_aud
(
    id           bigserial not null,
    post_id      bigint,
    folder       varchar(100),
    file_name    varchar(200),
    created_by   varchar(255),
    created_date timestamp,
    updated_by   varchar(255),
    updated_date timestamp,
    REV          int4      not null,
    REVTYPE      int2,
    primary key (id, REV),
    CONSTRAINT FK_post_documents_aud_2_revision FOREIGN KEY (REV)
        REFERENCES common_revision_entity (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

create table messages
(
    lang         varchar(2)   not null,
    message_code varchar(255) not null,
    translated   varchar(255) not null,
    created_by   varchar(255) not null,
    created_date timestamp    not null,
    updated_by   varchar(255) not null,
    updated_date timestamp    not null,
    version      bigint,
    constraint messages_pkey primary key (lang, message_code)
);

create table messages_aud
(
    rev          integer      not null,
    revtype      smallint,
    lang         varchar(2)   not null,
    message_code varchar(255) not null,
    translated   varchar(255),
    created_by   varchar(255),
    created_date timestamp,
    updated_by   varchar(255),
    updated_date timestamp,
    version      bigint,
    constraint messages_aud_pkey primary key (lang, message_code, rev),
    constraint fk_messages_aud_2_revision foreign key (rev)
        references common_revision_entity (id)
);
