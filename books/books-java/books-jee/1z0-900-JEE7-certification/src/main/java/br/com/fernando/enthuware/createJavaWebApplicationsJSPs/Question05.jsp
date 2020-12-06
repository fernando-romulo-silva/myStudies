<%@ taglib prefix="c" uri="http://xmlns.jcp.org/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page isErrorPage="true"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<body>
   
   <c:out value="${requestScope['javax.servlet.error.exception']}"/>
   
   
   <% exception.getMessage(); %>

</body>
</html>