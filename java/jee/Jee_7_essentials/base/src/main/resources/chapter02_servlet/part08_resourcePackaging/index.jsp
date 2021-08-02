<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link href="css/common.css" rel="stylesheet" type="text/css">
<title>This is a simple Page</title>
</head>
<body>
	<!-- from app.war -->
	<img src="${pageContext.request.contextPath}/images/principal.jpg" />
	<br />

	<div class="hero-image">
		<h1>Super Test</h1>
	</div>
	<!-- from lib.jar -->
	<img src="images/footer.png" />
</body>
</html>
