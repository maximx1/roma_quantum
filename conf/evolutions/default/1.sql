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

create table AUTH_CODES(
  AUTHORIZATION_CODE text not null,
  USER_ID integer not null,
  REDIRECT_URI text,
  CREATED_AT timestamp not null,
  SCOPE text,
  CLIENT_ID text,
  EXPIRES_IN integer not null
}

# --- !Downs
drop table if exists AUTH_CODES;
drop table if exists USERS;
drop sequence if exists USERS_ID_SEQ;