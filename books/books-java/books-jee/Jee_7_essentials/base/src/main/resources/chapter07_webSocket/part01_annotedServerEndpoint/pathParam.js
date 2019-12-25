var output = document.getElementById("output");

function sayHello() {

	var wsUri = "ws://localhost:18080/embeddedJeeContainerTest/greet/" + who.value;

	console.log(wsUri);

	var websocket = new WebSocket(wsUri);

	websocket.onopen = function() {
		writeToScreen("CONNECTED");
		websocket.send(greeting.value);
		writeToScreen("SENT (text): " + greeting.value);
	};

	websocket.onmessage = function(evt) {
		writeToScreen("RECEIVED: " + evt.data);
	};

	websocket.onerror = function(evt) {
		writeToScreen('<span style="color: red;">ERROR:</span> ' + evt.data);
	};
}

function writeToScreen(message) {
	var pre = document.createElement("p");
	pre.style.wordWrap = "break-word";
	pre.innerHTML = message;
	output.appendChild(pre);
}
