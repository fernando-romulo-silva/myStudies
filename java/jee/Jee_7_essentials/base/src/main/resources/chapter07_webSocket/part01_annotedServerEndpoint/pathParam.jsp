<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>WebSocket : Path Parameters</title>

</head>
<body>
	<h1>WebSocket : Path Parameters</h1>

	<div style="text-align: center;">
		<form action="">
			Name: <input id="who" value="Duke" type="text"><br>
			<!--  -->

			Greeting: <input id="greeting" value="Hello" type="text"><br>
			<!--  -->

			<input onclick="sayHello()" value="Greet" type="button"> <br>
		</form>
	</div>
	
	<p>Entered text is sent as a path parameter</p>
	<div id="output"></div>
	<script type="text/javascript" src="pathParam.js">
	
	</script>
</body>
</html>
