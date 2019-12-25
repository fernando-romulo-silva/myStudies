var wsUri = "ws://localhost:18080/embeddedJeeContainerTest/encoder";
var websocket = new WebSocket(wsUri);
websocket.onopen = function(evt) { onOpen(evt) };
websocket.onmessage = function(evt) { onMessage(evt) };
websocket.onerror = function(evt) { onError(evt) };

var output = document.getElementById("output");

//function init() {
//    
//}

function echoJson() {
    websocket.send(dataField.value);
    writeToScreen("SENT: " + dataField.value);
}

function onOpen() {
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

//window.addEventListener("load", init, false);
