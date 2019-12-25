
// You can invoke a WebSocket endpoint using the W3C-defined JavaScript API. 
// The API allows us to connect to a WebSocket endpoint by specifying the URL and an optional
// list of subprotocols:
var wsUri = "ws://localhost:18080/embeddedJeeContainerTest/websocket";

// • We invoke the WebSocket constructor by specifying the URI where the endpoint is published.
//
// • The ws:// protocol scheme defines the URL to be a WebSocket endpoint. The wss:// scheme may be used to initiate a secure connection.
//
// • WebSocket endpoint is hosted at localhost host and port 18080.
//
// • Application is deployed at the myapp context root.
//
// • The endpoint is published at the /chat URI.
//
// • You can specify an optional array of subprotocols in the constructor; the default value is an empty array.
//
// • An established WebSocket connection is available in the JavaScript websocket variable

console.log("Connecting to " + wsUri);

var websocket = new WebSocket(wsUri);

// • The onopen event handler is called when a new connection is initiated.
websocket.onopen = function(evt) { onOpen(evt); };

//A message can be received via the onmessage event handler:
websocket.onmessage = function(evt) { onMessage(evt); };

// • The onerror event handler is called when an error is received during the communication.
websocket.onerror = function(evt) { onError(evt); };

// • The onclose event handler is called when the connection is terminated:
websocket.onclose = function(evt) { onClose(evt); };

var output = document.getElementById("output");

// Text or binary data can be sent via any of the send methods:
function echoText() {
    console.log("echoText: " + myField.value);
    websocket.send(myField.value);
    writeToScreen("SENT (text): " + myField.value);
}

// This code reads the value entered in a text field, myField, and sends it as a text message:
function echoBinary() {
    var buffer = new ArrayBuffer(myField2.value.length);
    var bytes = new Uint8Array(buffer);
    for (var i=0; i<bytes.length; i++) {
        bytes[i] = i;
    }
    websocket.send(buffer);
    writeToScreen("SENT (binary): " + buffer.byteLength + " bytes");
}

function onOpen() {
    console.log("onOpen");
    writeToScreen("CONNECTED");
}

function onMessage(evt) {
    if (typeof evt.data === "string") {
        writeToScreen("RECEIVED (text): " + evt.data);
    } else {
        writeToScreen("RECEIVED (binary): " + evt.data);
    }
}

function onError(evt) {
    writeToScreen('<span style="color: red;">ERROR:</span> ' + evt.data);
}

function onClose(evt) {
    writeToScreen('<span style="color: red;">CLOSED:</span> ' + evt.data);
}

function writeToScreen(message) {
    var pre = document.createElement("p");
    pre.style.wordWrap = "break-word";
    pre.innerHTML = message;
    output.appendChild(pre);
}
