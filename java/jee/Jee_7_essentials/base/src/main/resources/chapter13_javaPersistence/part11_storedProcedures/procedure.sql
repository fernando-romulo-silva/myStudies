CREATE TABLE PERSON("ID" INTEGER not null primary key, "FIRST_NAME" VARCHAR(50) not null, "LAST_NAME" VARCHAR(50) not null, "ADDRESS" VARCHAR(250) not null)/;

CREATE TABLE PERSON_HISTORY AS (SELECT * FROM PERSON) WITH NO DATA/;

CREATE PROCEDURE FETCH_PERSON_HISTORY() 
READS SQL DATA DYNAMIC RESULT SETS 1
 BEGIN ATOMIC
  DECLARE history_cursor CURSOR FOR SELECT * FROM PERSON_HISTORY;
  open history_cursor;
 END/;

CREATE PROCEDURE MOVE_TO_HISTORY(IN person_id_in INT, OUT status_out BOOLEAN)
 MODIFIES SQL DATA
 BEGIN ATOMIC

 DECLARE temp_count INTEGER;
 SET temp_count = -1;
 SET status_out = FALSE;

  select count(*) 
    into temp_count
    from PERSON p 
   where p.id = person_id_in;
  
   if temp_count > -1  THEN
      SET status_out = TRUE;
      insert into PERSON_HISTORY (select * from PERSON p where p.id = person_id_in);
      delete from PERSON p where p.id = person_id_in;
   end if;
 END/;