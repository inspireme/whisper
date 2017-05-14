/*==============================================================*/
/* DBMS name:      ORACLE Version 10gR2                         */
/* Created on:     2016-08-24 22:24:47                          */
/*==============================================================*/

drop table USERS cascade constraints;

drop table AUTHORITIES cascade constraints;

drop table ROLES cascade constraints;

drop table R_ROLES_AUTHORITIES cascade constraints;

drop table R_USERS_ROLES cascade constraints;

drop sequence SEQ_AUTHORITIES;

drop sequence SEQ_ROLES;

drop sequence SEQ_USERS;

-------------------------------------------------------------
create sequence SEQ_USERS
minvalue 1
maxvalue 9999999999999999999999
start with 1
increment by 1
cache 1000;

create sequence SEQ_AUTHORITIES
minvalue 1
maxvalue 9999999999999999999999
start with 1
increment by 1
cache 1000;

create sequence SEQ_ROLES
minvalue 1
maxvalue 9999999999999999999999
start with 1
increment by 1
cache 1000;

/*==============================================================*/
/* Table: AUTHORITIES                                           */
/*==============================================================*/
create table AUTHORITIES  (
   ID                   NUMBER                         not null,
   CODE                 VARCHAR2(64),
   DISPLAY_NAME         VARCHAR2(64),
   AUTH_URL             VARCHAR2(64),
   AUTH_TYPE            VARCHAR(3),
   AUTH_INDEX            NUMBER  ,
   PARENT                NUMBER,
   REMARK                VARCHAR2(128),
    "CREATE_TIME" TIMESTAMP NOT NULL ENABLE,
	"UPDATE_TIME" TIMESTAMP ,
	"DELETE_FLAG" CHAR not null,
   constraint PK_AUTHORITIES primary key (ID)
);

comment on table AUTHORITIES is
'メニュー管理';

comment on column AUTHORITIES.ID is
'UID';

comment on column AUTHORITIES.CODE is
'コード';

comment on column AUTHORITIES.DISPLAY_NAME is
'メニュー名称';

comment on column AUTHORITIES.AUTH_URL is
'URL,#@AUTH_TYPEが0（権限）だった場合、「/」になる＠AUTH_TYPEが1（メニュー）だった場合、「/XXX/XX.jp」なのようになる';

comment on column AUTHORITIES.AUTH_TYPE is
'タイプ,#@0:権限@1:メニュー';

comment on column AUTHORITIES.AUTH_INDEX is
'並び順';

comment on column AUTHORITIES.PARENT is
'親ID';

comment on column AUTHORITIES.REMARK is
'備考';

/*==============================================================*/
/* Table: ROLES                                                 */
/*==============================================================*/
create table ROLES  (
   ID                   NUMBER                         not null,
   CODE                 VARCHAR2(32) ,
   NAME                 VARCHAR2(64),
   REMARK                VARCHAR2(128),
    "CREATE_TIME" TIMESTAMP NOT NULL ENABLE,
	"UPDATE_TIME" TIMESTAMP ,
	"DELETE_FLAG" CHAR not null,
   constraint PK_ROLES primary key (ID)
);

comment on table ROLES is
'ユーザータイプ';

comment on column ROLES.ID is
'UID';

comment on column ROLES.CODE is
'コード';

comment on column ROLES.NAME is
'名称';

comment on column ROLES.REMARK is
'備考';

/*==============================================================*/
/* Table: USERS                                                 */
/*==============================================================*/
create table USERS  (
   ID                   NUMBER                         not null,
   LOGIN_ID             VARCHAR2(128),
   PASSWORD             VARCHAR2(128),
   NAME                 VARCHAR2(128),
   EMAIL                VARCHAR2(128),
   PHONE                VARCHAR2(64),
	MOBILE                  VARCHAR2(64),
	DEPARTMENT                 VARCHAR2(256)  ,
    "CREATE_TIME" TIMESTAMP NOT NULL ENABLE,
	"UPDATE_TIME" TIMESTAMP ,
	"DELETE_FLAG" CHAR not null,
   constraint PK_USERS primary key (ID)
);

comment on table USERS is
'ユーザー情報';

comment on column USERS.ID is
'UID';

comment on column USERS.LOGIN_ID is
'ログインユーザー';

comment on column USERS.PASSWORD is
'パスワード';

comment on column USERS.NAME is
'名前';

comment on column USERS.EMAIL is
'メールアドレス';

comment on column USERS.PHONE is
'TEL';

comment on column USERS.MOBILE is
'携帯';

comment on column USERS.DEPARTMENT is
'事業部門';

/*==============================================================*/
/* Table: R_ROLES_AUTHORITIES                                   */
/*==============================================================*/
create table R_ROLES_AUTHORITIES  (
   ROLE_ID              NUMBER,
   AUTHORITY_ID         NUMBER,
    "CREATE_TIME" TIMESTAMP NOT NULL ENABLE,
	"UPDATE_TIME" TIMESTAMP ,
	"DELETE_FLAG" CHAR not null
);

comment on table R_ROLES_AUTHORITIES is
'ユーザータイプとメニュー';

comment on column R_ROLES_AUTHORITIES.ROLE_ID is
'ユーザータイプID';

comment on column R_ROLES_AUTHORITIES.AUTHORITY_ID is
'メニューID';

/*==============================================================*/
/* Index: AUTH_ROLE_FK                                          */
/*==============================================================*/
create index AUTH_ROLE_FK on R_ROLES_AUTHORITIES (
   ROLE_ID ASC
);

/*==============================================================*/
/* Index: ROLE_AUTH_FK                                          */
/*==============================================================*/
create index ROLE_AUTH_FK on R_ROLES_AUTHORITIES (
   AUTHORITY_ID ASC
);

/*==============================================================*/
/* Table: R_USERS_ROLES                                         */
/*==============================================================*/
create table R_USERS_ROLES  (
   USER_ID              NUMBER,
   ROLE_ID              NUMBER,
    "CREATE_TIME" TIMESTAMP NOT NULL ENABLE,
	"UPDATE_TIME" TIMESTAMP ,
	"DELETE_FLAG" CHAR not null
);

comment on table R_USERS_ROLES is
'ユーザーとユーザータイプ';

comment on column R_USERS_ROLES.ROLE_ID is
'ユーザータイプID';

comment on column R_USERS_ROLES.USER_ID is
'ユーザーID';

/*==============================================================*/
/* Index: ROLE_USER_FK                                          */
/*==============================================================*/
create index ROLE_USER_FK on R_USERS_ROLES (
   USER_ID ASC
);

/*==============================================================*/
/* Index: ROLE_USER_FK2                                         */
/*==============================================================*/
create index ROLE_USER_FK2 on R_USERS_ROLES (
   ROLE_ID ASC
);