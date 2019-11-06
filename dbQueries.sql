drop table if exists account;
create table account(user_id INT PRIMARY KEY, username VARCHAR(50) NOT NULL, email VARCHAR(50) NOT NULL);
insert into account(user_id, username, email) values (1, 'parthp', 'parth.patil@imaginea.com'), (2, 'ppatil', 'ppatil@plymouthrock.com');

insert into account(user_id, username, email) values (3, 'parthpatil3110', 'parthpatil3110@gmail.com');
drop table if exists role;
create table role(user_id INT PRIMARY KEY, role VARCHAR(50) NOT NULL);
insert into role(user_id, role) values (1, 'Administrator'), (2, 'Developer'), (3, 'User');