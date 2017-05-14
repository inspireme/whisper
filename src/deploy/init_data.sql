insert into WHISPER.AUTHORITIES values ('1','AUTH_PORTAL','ポータル','/portal/main','1',NULL,NULL,NULL,'2016/10/25 19:56:40',NULL,'0');
insert into WHISPER.AUTHORITIES values ('2','AUTH_USER_LIST','ユーザー管理','/user/list','1',NULL,NULL,NULL,'2016/10/25 19:57:52',NULL,'0');
insert into WHISPER.AUTHORITIES values ('3','AUTH_USER_EDIT','ユーザー編集','/user/edit','1',NULL,NULL,NULL,'2016/10/25 19:56:40',NULL,'0');
insert into WHISPER.AUTHORITIES values ('4','AUTH_AUTHORITY_LIST','権限管理','/authority/list','1',NULL,NULL,NULL,'2016/10/25 19:57:52',NULL,'0');
insert into WHISPER.AUTHORITIES values ('5','AUTH_AUTHORITY_EDIT','権限編集','/authority/edit','1',NULL,NULL,NULL,'2016/10/25 19:56:40',NULL,'0');

insert into WHISPER.ROLES values ('1','ROLE_USER','社員',NULL,'2016/10/25 20:08:16',NULL,'0');
insert into WHISPER.ROLES values ('2','ROLE_LEADER','リーダー',NULL,'2016/10/25 20:08:46',NULL,'0');
insert into WHISPER.ROLES values ('3','ROLE_ADMIN','システム管理者',NULL,'2016/10/25 20:09:15',NULL,'0');


insert into WHISPER.USERS values ('1','cndone','123','テストユーザー','cndone@gmail.com','13366542112','13366542112',NULL,'2016/10/10 13:01:46',NULL,'0');


insert into WHISPER.R_USERS_ROLES values ('1','3','2016/10/25 20:13:06',NULL,'0');


insert into WHISPER.R_ROLES_AUTHORITIES values ('3','1','2016/10/25 20:13:37',NULL,'0');
insert into WHISPER.R_ROLES_AUTHORITIES values ('3','2','2016/10/25 20:13:46',NULL,'0');
insert into WHISPER.R_ROLES_AUTHORITIES values ('3','3','2016/10/25 20:13:56',NULL,'0');