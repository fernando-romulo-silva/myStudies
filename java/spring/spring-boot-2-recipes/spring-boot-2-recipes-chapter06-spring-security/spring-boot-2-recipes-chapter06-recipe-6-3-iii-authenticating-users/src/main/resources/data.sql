 
insert into users (username, password, enabled) values ('admin@books.io', '{bcrypt}$2a$10$E3mPTZb50e7sSW15fDx8Ne7hDZpfDjrmMPTTUp8wVjLTu.G5oPYCO', true);

insert into users (username, password, enabled) values ('marten@books.io', '{bcrypt}$2a$10$5VWqjwoMYnFRTTmbWCRZT.iY3WW8ny27kQuUL9yPK1/WJcPcBLFWO', true);

insert into users (username, password, enabled) values ('jdoe@books.net', '{bcrypt}$2a$10$cFKh0.XCUOA9L.in5smIiO2QIOT8.6ufQSwIIC.AVz26WctxhSWC6', false);

insert into authorities (username, authority) values ('admin@books.io', 'ADMIN');

insert into authorities (username, authority) values ('admin@books.io', 'USER');

insert into authorities (username, authority) values ('marten@books.io', 'USER');

insert into authorities (username, authority) values ('jdoe@books.net', 'USER');