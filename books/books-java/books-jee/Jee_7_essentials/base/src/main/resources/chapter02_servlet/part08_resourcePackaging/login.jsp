<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
</head>
<body>
	<form method="POST" action="j_security_check">
		<label id="lb_j_username" for="j_username">User:</label> <input type="text" id="j_username" name="j_username" /> <br /> <label id="lb_j_password" for="j_password">Password:</label> <input type="password" name="j_password" autocomplete="off"> <br /> <input type="button" value="submit" /> <input type="reset" value="Reset">
	</form>
</body>
</html>