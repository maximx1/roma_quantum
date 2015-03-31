# --- !Ups
create table USERS(
	ID uuid not null,
	FIRST_NAME text not null,
	LAST_NAME text not null,
	EMAIL text not null,
    PASSKEY text not null,
    CREATED_AT timestamp not null,
    CREATED_BY_UUID uuid not null,
    UPDATED_AT timestamp not null,
    UPDATED_BY_UUID uuid not null,
    DELETED_AT timestamp not null,
    DELETED_BY_UUID uuid not null,
	primary key(ID)
);

create table CLIENTS(
    ID text not null,
    SECRET text,
    REDIRECT_URI text,
    SCOPE text,
    primary key(ID)
);

create sequence GRANT_TYPES_ID_SEQ;
create table GRANT_TYPES(
    ID integer not null default nextval('GRANT_TYPES_ID_SEQ'),
    GRANT_TYPE text,
    primary key(ID)
);

create table ACCESS_TOKENS(
    ACCESS_TOKEN text not null,
    REFRESH_TOKEN text,
    USER_ID uuid not null,
    SCOPE text,
    EXPIRES_IN bigint,
    CREATED_AT timestamp not null,
    CLIENT_ID text
    primary key(ACCESS_TOKEN),
    foreign key(USER_ID) references Users(ID),
    foreign key(CLIENT_ID) references CLIENTS(ID)
);

create table AUTH_CODES(
    AUTHORIZATION_CODE text not null,
    USER_ID uuid not null,
    REDIRECT_URI text,
    CREATED_AT timestamp not null,
    SCOPE text,
    CLIENT_ID text,
    EXPIRES_IN integer not null,
    primary key(AUTHORIZATION_CODE),
    foreign key(USER_ID) references Users(ID),
    foreign key(CLIENT_ID) references CLIENTS(ID)
);

create table CLIENT_GRANT_TYPES
    CLIENT_ID text not null,
    GRANT_TYPE_ID integer not null,
    primary key(CLIENT_ID, GRANT_TYPE_ID),
    foreign key(CLIENT_ID) references CLIENTS(ID),
    foreign key(GRANT_TYPE_ID) references GRANT_TYPES(ID)
);

create sequence CONFIRMATION_TOKENS_ID_SEQ;
create table CONFIRMATION_TOKENS(
    ID bigint not null default nextval('CONFIRMATION_TOKENS_ID_SEQ'),
    UUID uuid not null,
    EMAIL text not null,
    CREATION_TIME timestamp not null,
    EXPIRATION_TIME timestamp not null,
    IS_SIGN_UP boolean not null
    primary key(ID),
);

# --- !Downs
drop table if exists AUTH_CODES;
drop table if exists ACCESS_TOKENS;
drop table if exists CLIENT_GRANT_TYPES;
drop table if exists CONFIRMATION_TOKENS;
drop table if exists CONFIRMATION_TOKENS_ID_SEQ;

drop table if exists GRANT_TYPES;
drop sequence if exists GRANT_TYPES_ID_SEQ;
drop table if exists CLIENTS;
drop table if exists USERS;