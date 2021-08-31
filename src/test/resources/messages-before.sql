delete from message;

insert into message(id,tag,text,user_id) values
 (1,'message','post1',1),
 (2,'message','post2',1),
 (3,'post','post3',2),
 (4,'post','post4',2);

 alter table message auto_increment 10;
