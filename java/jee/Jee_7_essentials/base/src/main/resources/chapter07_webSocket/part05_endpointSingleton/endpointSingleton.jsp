<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>WebSocket : Simple Annotated Endpoint - Singleton</title>

    </head>
    <body>
        <h1>WebSocket : Simple Annotated Endpoint - Singleton</h1>

        <h2>Click on "Concat" button multiple times to see the concatenated output coming from endpoint.</h2>
        <div style="text-align: center;">
            <form action=""> 
                <input onclick="concat();" value="Concat" type="button"> 
                <input id="myField" value="WebSocket" type="text"><br>
            </form>
        </div>
        
        <div id="output"></div>
        <script type="text/javascript" src="endpointSingleton.js">
        </script>
    </body>
</html>
