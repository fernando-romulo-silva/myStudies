<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<body>

	<%
	    final ServletContext servletContext = request.getServletContext();
		
		servletContext.setAttribute("colorList", "List");
	%>

</body>
</html>