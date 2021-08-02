package br.com.fernando.chapter07_webSocket.part03_annotedClientEndpoint;

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
import javax.websocket.ClientEndpoint;
import javax.websocket.ClientEndpointConfig;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.HandshakeResponse;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
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

public class AnnotedClientEndpoint01 {

    // ==================================================================================================================================================================
    // You can convert a POJO to a WebSocket client endpoint by using @ClientEndpoint:
    @ClientEndpoint(configurator = MyConfigurator.class)
    // The @ClientEndpoint decorates the class as a WebSocket client endpoint. The annotation can have the attributes described
    // * configurator: An optional custom configurator class used to provide custom configuration of new instances of this endpoint.
    //
    // This will be an implementation of ClientEndpointConfig.Configurator.
    // * encoders: Optional ordered array of encoders used by this endpoint.
    // * decoders: Optional ordered array of decoders used by this endpoint.
    // * subprotocols: Optional ordered array of WebSocket protocols supported by this endpoint.
    //
    public static class MyClient {

        // The open method is called when a new connection is established with this endpoint.
        // The parameter s provides more details about the other end of the connection.
        @OnOpen
        public void onOpen(Session session) {
            System.out.println("Connected to endpoint: " + session.getBasicRemote());
            try {
                String name = "Duke";
                System.out.println("Sending message to endpoint: " + name);

                // A new outbound message from the client to the endpoint can be sent during the connection initiation—for example, in the open method:
                session.getBasicRemote().sendText(name);
            } catch (IOException ex) {
                Logger.getLogger(MyClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        // The close method is called when the connection is terminated.
        // The parameter c provides more details about why a WebSocket connection was closed.
        @OnClose
        public void close(CloseReason c) {
            // . . .
        }

        // The error method is called when there is an error in the connection.
        // The parameter t provides more details about the error.
        @OnError
        public void processError(Throwable t) {
            t.printStackTrace();
        }

        // An inbound message from the endpoint can be received in any Java method decorated with @OnMessage:
        // Session is optional.
        @OnMessage
        public void processMessage(String message, Session session) {
            // The processMessage method is invoked when a message is received from the endpoint.
            //
            // The message parameter is bound to the payload of the message.
            //
            // The session parameter provides more details about the other end of the connection.
            System.out.println("Received message in client: " + message);
        }
    }

    // You can use an optional configurator attribute to specify a custom configuration class for configuring new instances of this endpoint:
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

    // As for annotation-based server endpoints, the WebSocket annotation behaviors are not passed down the Java class inheritance hierarchy. 
    // They apply only to the Java class on which they are marked.

    // ==================================================================================================================================================================

    @ServerEndpoint("/websocket")
    public static class MyEndpoint {

        @OnMessage
        public String sayHello(String name) {
            System.out.println("Received message in endpoint : " + name);
            return "Hello " + name;
        }
    }

    // ==================================================================================================================================================================
    @WebServlet("/TestLocalClient")
    public static class TestLocalClient extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
                container.connectToServer(MyClient.class, URI.create(uri));
                out.println("<br><br>Look in server.log for log messages from message exchange between client/server.");

                out.println("</body>");
                out.println("</html>");

            } catch (DeploymentException ex) {
                Logger.getLogger(TestLocalClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @WebServlet("/TestRemoteClient")
    public static class TestRemoteClient extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            response.setContentType("text/html;charset=UTF-8");

            try (PrintWriter out = response.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet TestRemoteClient</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Servlet TestRemoteClient at " + request.getContextPath() + "</h1>");

                WebSocketContainer container = ContainerProvider.getWebSocketContainer();
                String uri = "ws://echo.websocket.org:80/";
                out.println("Connecting to " + uri);
                container.connectToServer(MyClient.class, URI.create(uri));
                out.println("<br><br>Look in server.log for log messages from message exchange between client/server.");

                out.println("</body>");
                out.println("</html>");
            } catch (DeploymentException ex) {
                Logger.getLogger(TestRemoteClient.class.getName()).log(Level.SEVERE, null, ex);
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
                    TestLocalClient.class, //
                    MyConfigurator.class, // 
                    TestRemoteClient.class);

            war.addWebResourceFiles(EmbeddedResource.add("src/main/resources/chapter07_webSocket/part03_annotedClientEndpoint/index.jsp"));

            final File warFile = war.exportToFile(APP_FILE_TARGET);

            embeddedJeeServer.start(HTTP_PORT);
            embeddedJeeServer.deploy(EMBEDDED_JEE_TEST_APP_NAME, warFile.getAbsolutePath());

            // --------------------------------------------------------------------------------------------------------------------------------
            // The client can connect to the endpoint via ContainerProvider:

            // ContainerProvider uses the ServiceLoader mechanism to load an implementation of ContainerProvider and provide a new instance of WebSocketContainer.
            // WebSocketContainer allows us to initiate a WebSocket handshake with the endpoint
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            String uri = "ws://localhost:" + HTTP_PORT + "/" + EMBEDDED_JEE_TEST_APP_NAME + "/websocket";

            // The server endpoint is published at the ws://localhost:8080/myApp/websocket URI.
            // The client connects to the endpoint by invoking the connectToServer method and providing the decorated client class and the URI of the endpoint.
            container.connectToServer(MyClient.class, URI.create(uri));

            // This method blocks until the connection is established, or throws an error if either the connection could not be made or there was a
            // problem with the supplied endpoint class.

            final HttpClient httpClient = HttpClientBuilder.create().build();

            final HttpResponse response01 = httpClient.execute(new HttpGet("http://localhost:" + HTTP_PORT + "/" + EMBEDDED_JEE_TEST_APP_NAME + "/TestLocalClient"));
            System.out.println(response01);

            final HttpResponse response02 = httpClient.execute(new HttpGet("http://localhost:" + HTTP_PORT + "/" + EMBEDDED_JEE_TEST_APP_NAME + "/TestRemoteClient"));
            System.out.println(response02);

        } catch (final Exception ex) {
            System.out.println(ex);
        }

        downVariables();
    }

}
