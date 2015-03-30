# --- !Ups
create sequence USERS_ID_SEQ;
create table USERS(
	ID integer not null default nextval('USERS_ID_SEQ'),
	FIRST_NAME text not null,
	LAST_NAME text not null,
	NICKNAME text not null,
	SYSTEM_KEY UUID not null,
	EMAIL text not null,
	primary key(ID)
);

create sequence CLIENTS_ID_SEQ;
create table CLIENTS(
    ID integer not null default nextval('CLIENTS_ID_SEQ'),
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
    USER_ID integer not null,
    SCOPE text,
    EXPIRES_IN integer,
    CREATED_AT timestamp not null,
    CLIENT_ID text
    primary key(ACCESS_TOKEN),
    foreign key(USER_ID) references Users(ID),
    foreign key(CLIENT_ID) references CLIENTS(ID)
);

create table AUTH_CODES(
    AUTHORIZATION_CODE text not null,
    USER_ID integer not null,
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
    CLIENT_ID integer not null,
    GRANT_TYPE_ID integer not null,
    primary key(CLIENT_ID, GRANT_TYPE_ID),
    foreign key(CLIENT_ID) references CLIENTS(ID),
    foreign key(GRANT_TYPE_ID) references GRANT_TYPES(ID)
);

create sequence CONFIRMATION_TOKENS_ID_SEQ;
create table CONFIRMATION_TOKENS(
    ID integer not null default nextval('CONFIRMATION_TOKENS_ID_SEQ'),
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

drop table if exists GRANT_TYPES;
drop sequence if exists GRANT_TYPES_ID_SEQ;
drop table if exists CLIENTS;
drop sequence if exists CLIENTS_ID_SEQ;
drop table if exists USERS;
drop sequence if exists USERS_ID_SEQ;