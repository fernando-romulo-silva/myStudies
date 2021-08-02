package br.com.fernando.chapter07_webSocket.part04_programmaticClientEndpoint;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.ClientEndpointConfig;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.HandshakeResponse;
import javax.websocket.MessageHandler;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import javax.websocket.server.ServerEndpoint;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;

import br.com.fernando.chapter07_webSocket.part03_annotedClientEndpoint.AnnotedClientEndpoint01.TestRemoteClient;

public class ProgrammaticClientEndpoint01 {

    // ==================================================================================================================================================================
    // You can also create a WebSocket client endpoint by extending the Endpoint class. Such an endpoint is also called a programmatic endpoint:
    public static class MyClient extends Endpoint {

        // In this code, the onOpen method is called when a new connection is initiated.
        // Endpoint Config identifies the configuration object used to configure this endpoint.
        @SuppressWarnings("unused")
        @Override
        public void onOpen(final Session session, final EndpointConfig ec) {

            // This endpoint is configured via multiple MessageHandlers, as for the interface-based server endpoint.
            // You can receive the whole message by registering the Messagehandler.Whole<T> handler, where T is a String for text
            // messages, and ByteBuffer or byte[] is for binary messages.
            //
            final MessageHandler.Whole<String> wholeStringMessageHandler = new MessageHandler.Whole<String>() {

                @Override
                public void onMessage(String text) {
                    System.out.println("Received response in client from endpoint: " + text);
                }
            };

            // You receive a multipart message by overriding MessageHandler.Partial<T>.
            final MessageHandler.Partial<String> wholePartialMessageHandler = new MessageHandler.Partial<String>() {

                @Override
                public void onMessage(String text, boolean partial) {
                    System.out.println("boolean(text) " + partial);
                    System.out.println("text length " + text.length());

                    System.out.println("Received response in client from endpoint: " + text);
                }

            };

            // session.addMessageHandler(wholeStringMessageHandler);
            session.addMessageHandler(wholePartialMessageHandler);

            System.out.println("Connected to endpoint: " + session.getBasicRemote());

            try {
                String name = "Duke";
                System.out.println("Sending message from client -> endpoint: " + name);

                // Similarly, you can initiate a synchronous or an asynchronous communication with the other end of the communication using
                // session.getBasicRemote and session.getAsyncRemote, respectively.
                session.getBasicRemote().sendText(name);

            } catch (IOException ex) {
                Logger.getLogger(MyClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override
        public void onClose(Session session, CloseReason closeReason) {
            super.onClose(session, closeReason);
        }

        @Override
        public void onError(Session session, Throwable t) {
            t.printStackTrace();
        }
    }

    // You can configure a programmatic client endpoint by providing an instance of ClientEndpointConfig.Configurator:
    public static class MyConfigurator extends ClientEndpointConfig.Configurator {

        // The MyConfigurator class provides an implementation of ClientEndpointConfig.Configurator.
        // This abstract class provides two methods to configure the client endpoint: beforeRequest and afterResponse.
        //
        // The beforeRequest method is called after the handshake request that will be used to initiate the connection to the server is formulated,
        // but before any part of the request is sent.
        @Override
        public void beforeRequest(Map<String, List<String>> headers) {
            System.out.println("beforeRequest:");
            for (String h : headers.keySet()) {
                for (String k : headers.get(h)) {
                    System.out.println("Header: " + h + ", " + k);
                }
            }
        }

        // The afterRes ponse method is called after a handshake response is received from the server as a result of a handshake interaction it initiated.
        @Override
        public void afterResponse(HandshakeResponse hr) {
            System.out.println("afterResponse:");
            for (String h : hr.getHeaders().keySet()) {
                for (String k : hr.getHeaders().get(h)) {
                    System.out.println("Header: " + h + ", " + k);
                }
            }
        }
    }

    @ServerEndpoint(value = "/websocket")
    public static class MyEndpoint {

        @OnMessage
        public String sayHello(String name) {
            System.out.println("Received message in endpoint : " + name);
            return "Hello " + name;
        }
    }

    @WebServlet(urlPatterns = { "/TestClient" })
    public static class TestClient extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @Override
        protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet TestServlet</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Servlet TestServlet at " + request.getContextPath() + "</h1>");

                WebSocketContainer container = ContainerProvider.getWebSocketContainer();
                String uri = "ws://localhost:" + request.getLocalPort() + request.getContextPath() + "/websocket";
                out.println("Connecting to " + uri);
                container.connectToServer(MyClient.class, null, URI.create(uri));
                out.println("<br><br>Look in server.log for message exchange between client/server.");

                out.println("</body>");
                out.println("</html>");
            } catch (DeploymentException ex) {
                Logger.getLogger(TestClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    // ==================================================================================================================================================================
    public static void main(String[] args) throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer();) {

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);

            war.addClasses( //
                    MyClient.class, //
                    MyEndpoint.class, //
                    TestClient.class, //
                    MyConfigurator.class, //
                    TestRemoteClient.class);

            war.addWebResourceFiles(EmbeddedResource.add("src/main/resources/chapter07_webSocket/part03_annotedClientEndpoint/index.jsp"));

            final File warFile = war.exportToFile(APP_FILE_TARGET);

            embeddedJeeServer.start(HTTP_PORT);
            embeddedJeeServer.deploy(EMBEDDED_JEE_TEST_APP_NAME, warFile.getAbsolutePath());

            String uri = "ws://localhost:" + HTTP_PORT + "/" + EMBEDDED_JEE_TEST_APP_NAME + "/websocket";

            // --------------------------------------------------------------------------------------------------------------------------------
            // The programmatic client endpoint can connect to the endpoint via ContainerProvider::

            // ContainerProvider uses the ServiceLoader mechanism to load an implementation of ContainerProvider and provide a new instance of WebSocketContainer.
            // WebSocketContainer allows us to initiate a WebSocket handshake with the endpoint
            final WebSocketContainer container = ContainerProvider.getWebSocketContainer();

            // This configuration element can be specified in connectToServer:
            container.connectToServer(MyClient.class, ClientEndpointConfig.Builder.create().configurator(new MyConfigurator()).build(), URI.create(uri));

            // The server endpoint is published at the ws://localhost:8080/myApp/websocket URI.
            // The client connects to the endpoint by invoking the connectToServer method and providing the decorated client class and the URI of the endpoint.

            // This method blocks until the connection is established, or throws an error if either the connection could not be made or there was a
            // problem with the supplied endpoint class.

            final HttpClient httpClient = HttpClientBuilder.create().build();

            final HttpResponse response01 = httpClient.execute(new HttpGet("http://localhost:" + HTTP_PORT + "/" + EMBEDDED_JEE_TEST_APP_NAME + "/TestClient"));
            System.out.println(response01);
        } catch (final Exception ex) {
            System.out.println(ex);
        }

        downVariables();
    }

}
