SET GLOBAL log_bin_trust_function_creators = 1;
CREATE FUNCTION getFirstNameById(in_id INT) RETURNS VARCHAR(60) BEGIN RETURN (SELECT first_name FROM singer WHERE id = in_id); END; 