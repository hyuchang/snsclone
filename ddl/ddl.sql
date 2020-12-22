create table USERS
(
    ID BIGINT auto_increment,
    EMAIL VARCHAR(50) not null,
    CREATE_AT DATETIME default CURRENT_TIMESTAMP not null,
    PWD VARCHAR(100) not null,
    STATUS TINYINT default 1 not null,
    NICKNAME VARCHAR(10),
    ACL TINYINT,
    constraint USERS_PK
        primary key (ID)
);

comment on column USERS.ACL is '0:public, 1:private';

create unique index USERS_EMAIL_UINDEX
    on USERS (EMAIL);

create table RELATION
(
    REQUESTER_ID BIGINT not null,
    SOMEONE_ID BIGINT not null,
    STATUS TINYINT,
    REQUEST_AT DATETIME default CURRENT_TIMESTAMP,
    RELATED_AT DATETIME,
    constraint RELATION_PK
        primary key (REQUESTER_ID, SOMEONE_ID)
);

create table NOTIFICATION
(
    ID BIGINT auto_increment,
    POST_ID BIGINT,
    TO_USER_ID BIGINT not null,
    FROM_USER_ID BIGINT not null,
    IS_READ TINYINT default 0,
    TYPE TINYINT,
    DESCRIPTION VARCHAR2(100),
    CREATE_AT DATE default CURRENT_TIMESTAMP,
    constraint NOTIFICATION_PK
        primary key (ID)
);

comment on column NOTIFICATION.TYPE is '1:like, 2:comment';

create index NOTIFICATION__INDEX
    on NOTIFICATION (TO_USER_ID);

create index NOTIFICATION_FROM_USER_ID_INDEX
    on NOTIFICATION (FROM_USER_ID);

create table POST_LIKE
(
    POST_ID BIGINT not null,
    USER_ID BIGINT not null,
    CREATE_AT DATETIME default CURRENT_TIMESTAMP not null,
    constraint POST_LIKE_PK
        primary key (POST_ID, USER_ID)
);

create table POST_IMAGE
(
    POST_ID BIGINT auto_increment,
    PATH VARCHAR2(100) not null,
    ID BIGINT auto_increment,
    constraint POST_IMAGE_PK
        primary key (ID)
);

create index POST_IMAGES_POST_ID_INDEX
    on POST_IMAGE (POST_ID desc);

create table POST_COMMENT
(
    ID BIGINT auto_increment,
    USER_ID BIGINT not null,
    POST_ID BIGINT not null,
    COMMENT VARCHAR2(255) not null,
    CREATE_AT DATETIME default CURRENT_TIMESTAMP not null,
    constraint POST_COMMENT_PK
        primary key (ID)
);

create index POST_COMMENT_POST_ID_INDEX
    on POST_COMMENT (POST_ID);

create table POST
(
    ID BIGINT auto_increment,
    USER_ID BIGINT not null,
    LIKE_CNT BIGINT default 0,
    COMMENT_CNT BIGINT default 0,
    CREATE_AT DATETIME default CURRENT_TIMESTAMP,
    DESCRIPTION TEXT not null,
    ACL INT default 0 not null,
    constraint POST_PK
        primary key (ID)
);

comment on column POST.ACL is '0: public, 1:private';

create index POST_USERID_INDEX
    on POST (USER_ID desc);

