package br.com.fernando.chapter07_webSocket.part09_chat;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.connectToServerWebSockets;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.websocket.ClientEndpoint;
import javax.websocket.EncodeException;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;

public class Chat {

    @ServerEndpoint("/chat")
    public static class ChatEndpoint {

        @OnMessage
        public void message(String message, Session client) throws IOException, EncodeException {
            System.out.println("message: " + message);
            for (Session peer : client.getOpenSessions()) {
                peer.getBasicRemote().sendText(message);
            }
        }
    }

    @ClientEndpoint
    public static class ChatClientEndpoint1 {

        public static String TEXT = "Client1 joins";

        public static CountDownLatch latch;

        public static String response;

        @OnOpen
        public void onOpen(Session session) {
            try {
                session.getBasicRemote().sendText(TEXT);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }

        @OnMessage
        public void processMessage(String message) {
            response = message;
            latch.countDown();
        }
    }

    @ClientEndpoint
    public static class ChatClientEndpoint2 {

        public static String TEXT = "Client2 joins";

        public static CountDownLatch latch;

        public static String response;

        @OnOpen
        public void onOpen(Session session) {
            try {
                session.getBasicRemote().sendText(TEXT);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }

        @OnMessage
        public void processMessage(String message) {
            response = message;
            latch.countDown();
        }
    }

    // ==================================================================================================================================================================
    public static void main(String[] args) throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer();) {

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);

            war.addClasses( //
                    ChatEndpoint.class, //
                    ChatClientEndpoint1.class, //
                    ChatClientEndpoint2.class);

            war.addWebResourceFiles(EmbeddedResource.add("src/main/resources/chapter07_webSocket/part09_chat/index.jsp"));
            war.addWebResourceFiles(EmbeddedResource.add("src/main/resources/chapter07_webSocket/part09_chat/websocket.js"));

            final File warFile = war.exportToFile(APP_FILE_TARGET);

            embeddedJeeServer.start(HTTP_PORT);
            embeddedJeeServer.deploy(EMBEDDED_JEE_TEST_APP_NAME, warFile.getAbsolutePath());

            // --------------------------------------------------------------------------------------------------------------------------------
            ChatClientEndpoint1.latch = new CountDownLatch(1);
            ChatClientEndpoint2.latch = new CountDownLatch(1);
            // 
            connectToServerWebSockets(ChatClientEndpoint1.class, "chat");
            ChatClientEndpoint1.latch.await(3, TimeUnit.SECONDS);
            System.out.println("ChatClientEndpoint1 :" + ChatClientEndpoint1.response);
            // 
            connectToServerWebSockets(ChatClientEndpoint2.class, "chat");
            ChatClientEndpoint2.latch.await(3, TimeUnit.SECONDS);
            System.out.println("ChatClientEndpoint2 :" + ChatClientEndpoint2.response);

            // --------------------------------------------------------------------------------------------------------------------------------
            final HttpClient httpClient = HttpClientBuilder.create().build();
            final HttpResponse response01 = httpClient.execute(new HttpGet("http://localhost:" + HTTP_PORT + "/" + EMBEDDED_JEE_TEST_APP_NAME + "/index.jsp"));
            System.out.println(response01);

        } catch (final Exception ex) {
            System.out.println(ex);
        }

        downVariables();
    }
}
