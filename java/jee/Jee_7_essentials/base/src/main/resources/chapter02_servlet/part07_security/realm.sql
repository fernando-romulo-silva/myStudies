
CREATE TABLE users(username varchar(255) NOT NULL, password varchar(255) DEFAULT NULL)

CREATE TABLE groups(username varchar(255) DEFAULT NULL, groupname varchar(255) DEFAULT NULL)

/* username=root; password=changeit(SHA-256, Base64)*/
INSERT INTO users VALUES('root','AIEM+LlNb8ucXeSE077EGHYgs+KHblmquQ2FL+Dxj7Y=')
/* username=john; password=changeit(SHA-256, Base64)*/
INSERT INTO users VALUES('john','AIEM+LlNb8ucXeSE077EGHYgs+KHblmquQ2FL+Dxj7Y=')
/* username=root; groupname=admin */
INSERT INTO groups VALUES('root','ROLE01')
/* username=john; groupname=user */
INSERT INTO groups VALUES('john','ROLE02')