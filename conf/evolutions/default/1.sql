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

# --- !Downs
drop table if exists USERS;
drop sequence if exists USERS_ID_SEQ;