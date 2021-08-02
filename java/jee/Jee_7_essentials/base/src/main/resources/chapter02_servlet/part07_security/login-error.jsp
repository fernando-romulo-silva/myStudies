<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Form-Based Login Error Page</title>
</head>
<body>
	<h1>Form-Based Login Error Page</h1>

	<h2>Invalid user name or password.</h2>

	<p>Please enter a user name or password that is authorized to access this application. For this application, make sure to create a user:
	<p>
	<p>

		For WildFly: Invoke "./bin/add-user.sh -a -u u1 -p p1 -g g1"<br> For GlassFish: Invoke "./bin/asadmin create-file-user --groups g1 u1" and use the password "p1" when prompted.<br> <br> Click here to <a href="index.jsp">Try Again</a>
	</p>

</body>
</html>