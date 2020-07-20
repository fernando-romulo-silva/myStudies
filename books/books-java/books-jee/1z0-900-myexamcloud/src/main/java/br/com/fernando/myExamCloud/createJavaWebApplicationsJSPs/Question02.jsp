<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<body>

	<%
	    request.setAttribute("orderNumber", "12345");

	    String orderID = (String) pageContext //
	    	.getRequest() //
	    	.getAttribute("orderNumber");
	%>

</body>
</html>