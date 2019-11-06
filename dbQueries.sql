drop table if exists account;
create table account(user_id INT PRIMARY KEY, username VARCHAR(50) NOT NULL, email VARCHAR(50) NOT NULL);
insert into account(user_id, username, email) values (1, 'parthp', 'parth.patil@imaginea.com'), (2, 'ppatil', 'ppatil@plymouthrock.com');