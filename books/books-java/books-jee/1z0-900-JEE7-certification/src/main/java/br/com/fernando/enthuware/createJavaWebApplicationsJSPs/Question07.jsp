<!-- There are 3 types of directives:  -->
<!-- 1. page : Affects the overall properties of the jsp page. -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isErrorPage="true"%>
<%@ page attribute = "value" %>
<!-- or -->
<jsp:directive.page attribute = "value" />

<!--  2. taglib : the set of significant tags a JSP container interprets can be extended through a "tag library" -->
<%@ taglib prefix="c" uri="http://xmlns.jcp.org/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- or -->
<jsp:directive.taglib uri = "uri" prefix = "prefixOfTag" />

<!-- 3. include : The include directive is used to substitute text and/or code at JSP page translation-time --> 
<%@ include file="Question01.jsp" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<body>
   

</body>
</html>