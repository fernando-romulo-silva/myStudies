CREATE USER 'prospring5_A'@'localhost' IDENTIFIED BY 'prospring5_A';
CREATE SCHEMA musicdb_a;
GRANT ALL PRIVILEGES ON musicdb_a . * TO 'prospring5_A'@'localhost';
FLUSH PRIVILEGES;

CREATE USER 'prospring5_B'@'localhost' IDENTIFIED BY 'prospring5_B';
CREATE SCHEMA musicdb_b;
GRANT ALL PRIVILEGES ON musicdb_b . * TO 'prospring5_B'@'localhost';
FLUSH PRIVILEGES;

/*in case of java.sql.SQLException: The server timezone value 'UTC' is unrecognized or represents more than one timezone. */
SET GLOBAL time_zone = '+3:00';