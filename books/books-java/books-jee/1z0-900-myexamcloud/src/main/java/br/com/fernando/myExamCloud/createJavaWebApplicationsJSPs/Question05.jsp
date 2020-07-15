<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://xmlns.jcp.org/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<body>
	  
  <c:set var="bookId" value="${param.Remove}"/>
	
  <table>
    <c:forEach var="employee" items="$employees" status="status">                                       
      <tr>
        <td><c:expr value="$status.count"/></td>
        <td><c:expr value="$employee.name"/></td>
      </tr>
    </c:forEach>
  </table>
	
</body>
</html>