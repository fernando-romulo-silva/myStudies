<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>WebSocket : Message Size</title>

</head>
<body>
	<h1>WebSocket : Message Size (max=6)</h1>

	<div style="text-align: center;">
		<form action="">
			<h2>Text Data</h2>
			<input onclick="echoText();" value="Echo" type="button"> <input
				id="myField" value="123456" type="text"><br>
		</form>
		<form action="">
			<h2>Binary Data</h2>
			<input onclick="echoBinary();" value="Echo" type="button"> <input
				id="myField2" value="123456" type="text"><br>
		</form>
	</div>
	<p />
	Connection is closed if more than 6 characters are sent.
	<p />
	<div id="output"></div>
	<script type="text/javascript" src="messageSize.js">
		
	</script>
</body>
</html>
