package br.com.fernando.chapter07_webSocket.part01_annotatedServerEndpoint;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;

public class AnnotatedServerEndPoint02 {

    // =================================================================================================================================================================
    // Use a 0..n String or Java primitive parameters annotated with @PathParam for server endpoints:
    @ServerEndpoint(value = "/greet/{name}")
    public static class GreetingBean {

        // @PathParam is used to annotate the 'name' method parameter on a server endpoint where a URI template has been used in the path
        // mapping of the ServerEndpoint annotation
        // The method parameter may be of type String, any Java primitive type, or any boxed version thereof.
        //
        @OnMessage
        public String sayHello(String payload, @PathParam("name") String name) {

            return payload + " " + name + "!";
        }

        // @OnMessage
        // Use an optional Session parameter:
        public void receiveMessage(String message, Session session) throws IOException {
            // Session indicates a conversation between two WebSocket endpoints and represents the other end of the connection.

            session.getBasicRemote().sendText("Some Text");

            // The method may have a void return type. Such a message is consumed at the endpoint without returning a response.
            //
            // The method may have String, ByteBuffer, byte[], any Java primitive or class equivalent, and any other class for which there is an
            // encoder as the return value. If a return type is specified, then a response is returned to the client.
        }
    }

    // =================================================================================================================================================================
    @ServerEndpoint(value = "/websocket")
    public static class MyEndpoint {

        // The maxMessageSize attribute may be used to define the maximum size of the message in bytes that this method will be able to process.
        // The maxMessageSize attribute only applies when the annotation is used to process whole messages, not to those methods that process messages in parts or
        // use a stream or reader parameter to handle the incoming message.
        @OnMessage(maxMessageSize = 6)
        public String echoText(String data) {
            return data;
        }

        @OnMessage(maxMessageSize = 6)
        public ByteBuffer echoBinary(ByteBuffer data) throws IOException {
            return data;
        }

        // In this code, if a message of more than 6 bytes is received, then an error is reported and the connection is closed.
        // You can receive the exact error code and message by intercepting the life-cycle callback using @OnClose.
        // The default value is -1 to indicate that there is no maximum.
        @OnClose
        public void onClose(CloseReason reason) {
            System.out.println("CLOSED: " + reason.getCloseCode() + ", " + reason.getReasonPhrase());
        }

        @OnError
        public void onError(Throwable t) {
            t.printStackTrace();
        }
    }

    // ==================================================================================================================================================================
    public static void main(String[] args) throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer();) {

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);

            war.addClasses( //
                    MyEndpoint.class, //
                    GreetingBean.class);

            war.addWebResourceFiles(EmbeddedResource.add("src/main/resources/chapter07_webSocket/part01_annotedServerEndpoint/pathParam.jsp"));
            war.addWebResourceFiles(EmbeddedResource.add("src/main/resources/chapter07_webSocket/part01_annotedServerEndpoint/pathParam.js"));

            war.addWebResourceFiles(EmbeddedResource.add("src/main/resources/chapter07_webSocket/part01_annotedServerEndpoint/messageSize.jsp"));
            war.addWebResourceFiles(EmbeddedResource.add("src/main/resources/chapter07_webSocket/part01_annotedServerEndpoint/messageSize.js"));

            final File warFile = war.exportToFile(APP_FILE_TARGET);

            embeddedJeeServer.start(HTTP_PORT);
            embeddedJeeServer.deploy(EMBEDDED_JEE_TEST_APP_NAME, warFile.getAbsolutePath());

            // --------------------------------------------------------------------------------------------------------------------------------
            // GreetingBean
            System.out.println("Access http://localhost:18080/embeddedJeeContainerTest/pathParam.jsp");
            // "ws://localhost:18080/embeddedJeeContainerTest/greet/" + who.value

            // --------------------------------------------------------------------------------------------------------------------------------
            // MyEndpoint
            System.out.println("Access http://localhost:18080/embeddedJeeContainerTest/messageSize.jsp");
            // "ws://localhost:18080/embeddedJeeContainerTest/messageSize/" + who.value

        } catch (final Exception ex) {
            System.out.println(ex);
        }

        downVariables();
    }
}
