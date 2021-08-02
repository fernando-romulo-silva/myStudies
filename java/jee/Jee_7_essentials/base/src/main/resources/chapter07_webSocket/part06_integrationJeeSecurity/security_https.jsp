<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>WebSocket : Security</title>

    </head>
    <body>
        <h1>WebSocket : Security</h1>

        <div style="text-align: center;">
            <form action=""> 
                <input onclick="echo();" value="Echo" type="button"> 
                <input id="myField" value="WebSocket" type="text"><br>
            </form>
        </div>
        
        <p/>If you see this page that means you've entered the username/password 
        credentials correctly. Make sure to create the user by giving the command.<p/><p/>
        Make sure to create a user:<br><br>
        
        For WildFly: Invoke "./bin/add-user.sh -a -u u1 -p p1 -g g1"<br>
        For GlassFish: Invoke "./bin/asadmin create-file-user --groups g1 u1" and use the password "p1" when prompted.<br><br>
        
        <div id="output"></div>
        
        <script type="text/javascript" src="security_https.js">
        </script>
    </body>
</html>
