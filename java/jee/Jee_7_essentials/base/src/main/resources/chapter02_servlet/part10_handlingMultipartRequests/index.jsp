<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Servlet :Handling Multipart Requests</title>
    </head>
    <body>
        <h1>Servlet :Handling Multipart Requests</h1>
		<form action="${pageContext.request.contextPath}/FileUploadServlet" enctype="multipart/form-data" method="POST">
 			<input type="file" name="myFile"><br>
 			<input type="Submit" value="Upload File"><br>
		</form>
    </body>
</html>
