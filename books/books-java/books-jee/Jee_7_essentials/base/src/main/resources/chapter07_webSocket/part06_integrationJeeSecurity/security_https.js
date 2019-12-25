
function echo() {
    var wsUri = "wss://localhost:19090/embeddedJeeContainerTest/websocket";
    
    console.log("Connecting to " + wsUri);
    var websocket = new WebSocket(wsUri);
    
    websocket.onopen = function() { 
    	writeToScreen("CONNECTED");
        websocket.send(myField.value);
        writeToScreen("SENT: " + myField.value);
    };
    
    websocket.onmessage = function(evt) { 
    	writeToScreen("RECEIVED: " + evt.data); 
    };
}

function writeToScreen(message) {
    var output = document.getElementById("output");
    var pre = document.createElement("p");
    pre.style.wordWrap = "break-word";
    pre.innerHTML = message;
    output.appendChild(pre);
}
