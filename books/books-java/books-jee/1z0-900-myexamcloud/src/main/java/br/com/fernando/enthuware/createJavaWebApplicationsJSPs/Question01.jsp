<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<body>

	<sql:query var="books">                       
       select * from PUBLIC.books where id = ? 
       <sql:param value="${bid}" />
	</sql:query>

</body>
</html>