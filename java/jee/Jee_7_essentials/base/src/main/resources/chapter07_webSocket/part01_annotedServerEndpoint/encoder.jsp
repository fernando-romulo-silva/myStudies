<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>WebSocket : Encoder and Decoder in Annotated Endpoint</title>

        
    </head>
    <body>
        <h1>WebSocket : Encoder and Decoder in Annotated Endpoint</h1>

        Type valid JSON in the textbox and click on "Echo JSON" button.<br><br>
        <div style="text-align: center;">
            <form action=""> 
                <input onclick="echoJson()" value="Echo JSON" type="button"> 
                <input id="dataField" name="name" value="{}" type="text"><br>
            </form>
        </div>
        <div id="output"></div>
        
        <script type="text/javascript" src="encoder.js">
            
        </script>
    </body>
</html>
