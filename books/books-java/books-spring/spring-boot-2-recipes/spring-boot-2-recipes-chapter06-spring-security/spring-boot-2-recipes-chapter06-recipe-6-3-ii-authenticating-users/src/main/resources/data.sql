 
insert into users (username, password, enabled) values ('admin@books.io', '{noop}secret', true);

insert into users (username, password, enabled) values ('marten@books.io', '{noop}user', true);

insert into users (username, password, enabled) values ('jdoe@books.net', '{noop}unknown', false);

insert into authorities (username, authority) values ('admin@books.io', 'ADMIN');

insert into authorities (username, authority) values ('admin@books.io', 'USER');

insert into authorities (username, authority) values ('marten@books.io', 'USER');

insert into authorities (username, authority) values ('jdoe@books.net', 'USER');