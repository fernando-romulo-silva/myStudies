CREATE TABLE Customer (CustomerId int, FirstName varchar(255), LastName varchar(255), EMail varchar(255), Phone varchar(255),  PRIMARY KEY (CustomerId))

CREATE TABLE Author (AuthorId int, FirstName varchar(255), LastName varchar(255), PRIMARY KEY (AuthorId))

CREATE TABLE Book (ISBN varchar(50), Title varchar(255), PubDate date, Format varchar(50), UnitPrice float,  PRIMARY KEY (ISBN))


CREATE TABLE Books_by_Author (AuthorId int, ISBN varchar(50))

ALTER TABLE Books_by_Author ADD FOREIGN KEY (AuthorId) REFERENCES Author(AuthorId)

ALTER TABLE Books_by_Author ADD FOREIGN KEY (ISBN) REFERENCES Book(ISBN)



INSERT INTO Customer VALUES(5000, 'John','Smith', 'john.smith@verizon.net', '555-340-1230')

INSERT INTO Customer VALUES(5001, 'Mary','Johson', 'mary.johnson@comcast.net', '555-123-4567')

INSERT INTO Customer VALUES(5002, 'Bob','Collins', 'bob.collins@yahoo.com', '555-012-3456')



INSERT INTO Book VALUES('142311339X', 'The Lost Hero (Heroes of Olympus, Book 1)','2010-10-12', 'Hardcover', '10.95')

INSERT INTO Book VALUES('068985223', 'The House of the Scorpion', '2002-01-01', 'Hardcover', '16.95')

INSERT INTO Book VALUES('068983434', 'The Ghost', '2010-01-01', 'Hardcover', '16.95')



INSERT INTO Author VALUES(1000, 'Rick', 'Riordan')

INSERT INTO Author VALUES(1001, 'Nancy', 'Farmer')

INSERT INTO Author VALUES(1002, 'Ally', 'Condie')



INSERT INTO Books_by_Author VALUES(1000, '142311339X')

INSERT INTO Books_by_Author VALUES(1001, '068985223')