<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<jsp:useBean id="guestBean" scope="request" class="br.com.fernando.myExamCloud.createJavaWebApplicationsJSPs.Question06.GuestBean" >       
   <jsp:setProperty name="guestBean" property="guestName" />                                  
</jsp:useBean>
                                                                               
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<body>

   <jsp:getProperty name="guestBean" property="guestName" />                                 
	
</body>
</html>