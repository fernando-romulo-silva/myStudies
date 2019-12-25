var wsUri = "ws://localhost:18080/embeddedJeeContainerTest/websocket";

console.log("Connecting to " + wsUri);

var websocket = new WebSocket(wsUri);

websocket.onopen = function(evt) { onOpen(evt) };

websocket.onmessage = function(evt) { onMessage(evt) };

websocket.onerror = function(evt) { onError(evt) };

var output = document.getElementById("output");

function concat() {
    console.log("concat: " + myField.value);
    websocket.send(myField.value);
    writeToScreen("SENT: " + myField.value);
}

function onOpen() {
    console.log("onOpen");
    writeToScreen("CONNECTED");
}

function onMessage(evt) {
    writeToScreen("RECEIVED: " + evt.data);
}

function onError(evt) {
    writeToScreen('<span style="color: red;">ERROR:</span> ' + evt.data);
}

function writeToScreen(message) {
    var pre = document.createElement("p");
    pre.style.wordWrap = "break-word";
    pre.innerHTML = message;
    output.appendChild(pre);
}