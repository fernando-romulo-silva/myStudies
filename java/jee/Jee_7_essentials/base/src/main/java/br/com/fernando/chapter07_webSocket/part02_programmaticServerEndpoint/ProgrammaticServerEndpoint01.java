package br.com.fernando.chapter07_webSocket.part02_programmaticServerEndpoint;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.connectToServerWebSockets;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.Decoder;
import javax.websocket.Encoder;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.HandshakeResponse;
import javax.websocket.MessageHandler;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.PongMessage;
import javax.websocket.SendHandler;
import javax.websocket.SendResult;
import javax.websocket.Session;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerApplicationConfig;
import javax.websocket.server.ServerEndpointConfig;

import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;

@SuppressWarnings("unused")
public class ProgrammaticServerEndpoint01 {

    // =================================================================================================================================================================
    // You can create a WebSocket server endpoint by extending the Endpoint class. Such an endpoint is also called a programmatic endpoint:
    public static class MyEndpoint extends Endpoint {

        // For endpoints deployed in the Java EE platform, full dependency injection support as described in the CDI specification is available.
        // Field, method, and constructor injection is available in all WebSocket endpoint classes. Interceptors may be enabled for these
        // classes using the standard mechanism:
        @Inject
        private User user;

        // In this code, the onOpen method is called when a new connection is initiated.
        // Endpoint Config identifies the configuration object used to configure this endpoint.
        @Override
        public void onOpen(final Session session, EndpointConfig ec) {

            // Multiple MessageHandlers may be registered in this method to process incoming text, binary, and pong messages.
            // However, only one MessageHandler per text, binary, or pong message may be registered per Endpoint:
            //
            // MessageHandler.Whole<String> handler is registered to handle the incoming text messages.
            // Although not required, a response can be sent to the other end of the connection synchronously
            final MessageHandler.Whole<String> wholeSyncMessageHandler = new MessageHandler.Whole<String>() {

                // The onMessage method of the handler is invoked when the message is received.
                // The parameter s is bound to the payload of the message.
                @Override
                public void onMessage(String text) {
                    try {
                        MyEndpointTextClient.latch.countDown();
                        session.getBasicRemote().sendText(text);
                    } catch (IOException ex) {
                        Logger.getLogger(MyEndpoint.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            };

            // A response may be returned asynchronously as well. The Session.getAsyncRemote method returns an instance of RemoteEndpoint.Async that can be used to
            // send messages asynchronously. Two variations are possible:
            // Fist:
            final MessageHandler.Whole<String> wholeAsyncMessageHandler01 = new MessageHandler.Whole<String>() {

                @Override
                public void onMessage(String text) {

                    // The sendXXX method returns before the message is transmitted. The returned Future
                    // object is used to track the progress of the transmission. The Future's get method returns
                    // null upon successful completion.

                    Future<Void> future = session.getAsyncRemote().sendText(text);

                    if (future.isDone()) {
                        try {
                            Object o = future.get();
                            System.out.println("MessageHandler Async First: " + o);
                        } catch (Exception ex) {
                            Logger.getLogger(MyEndpoint.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            };

            // Second:
            final MessageHandler.Whole<String> wholeAsyncMessageHandler02 = new MessageHandler.Whole<String>() {

                @Override
                public void onMessage(String text) {

                    // The onResult method of the registered handler is called once the message has been transmitted.
                    // The parameter sr indicates whether the message was sent successfully, and if not,
                    // it carries an exception to indicate what the problem was.
                    session.getAsyncRemote().sendText(text, new SendHandler() {

                        @Override
                        public void onResult(SendResult sr) {
                            System.out.println("MessageHandler Async Second: " + sr.isOK());
                        }
                    });
                }
            };

            // session.addMessageHandler(wholeSyncMessageHandler);
            session.addMessageHandler(wholeAsyncMessageHandler01);
            // session.addMessageHandler(wholeAsyncMessageHandler02);

            // The MessageHandler.Part<ByteBuffer> handler is registered to handle the incoming binary messages.
            session.addMessageHandler(new MessageHandler.Partial<ByteBuffer>() {

                // The onMessage method of the handler is invoked whenthe message is received.
                // The parameter t is bound to the payload of the message.

                // The Boolean parameter is true if the part received is the last part, and false otherwise.
                @Override
                public void onMessage(ByteBuffer t, boolean part) {
                    try {
                        MyEndpointBinaryClient.latch.countDown();
                        session.getBasicRemote().sendBinary(t);
                    } catch (IOException ex) {
                        Logger.getLogger(MyEndpoint.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

            // The MessageHandler.Whole<PongMessage> handler is registered to handle the incoming PongMessage.
            session.addMessageHandler(new MessageHandler.Whole<PongMessage>() {

                // The onMessage method of the handler is invoked when the message is received.
                // The parameter t is bound to the payload of the message.
                @Override
                public void onMessage(PongMessage t) {
                    System.out.println("PongMessage received: " + t.getApplicationData());
                }
            });

        }

        // The Endpoint.onClose and onError methods can be overridden to invoke other lifecycle callbacks.

        // In the onClose method, the c parameter provides more details about why the WebSocket connection was closed.
        @Override
        public void onClose(Session session, CloseReason closeReason) {
            System.err.println("Closing: " + closeReason.getReasonPhrase());
        }

        // Likewise, the t parameter provides more details about the error received.
        @Override
        public void onError(Session session, Throwable t) {
            System.err.println("Error: " + t.getLocalizedMessage());
        }
    }

    // =================================================================================================================================================================
    // You configure programmatic endpoints by implementing the ServerApplicationCon fig interface.
    // This interface provides methods to specify the WebSocket endpoints within an archive that must be deployed:
    //
    public static class MyEndpointConfig implements ServerApplicationConfig { // The MyEndpointConfig class implements the ServerApplicationConfig interface.

        @Override
        public Set<ServerEndpointConfig> getEndpointConfigs(Set<Class<? extends Endpoint>> set) {
            // The getEndpointConfig method provides a list of ServerEndpointConfig that is used to deploy the programmatic endpoints.
            // The URI of the endpoint is specified here as well.

            return new HashSet<ServerEndpointConfig>() {

                private static final long serialVersionUID = 1L;

                {
                    // You can configure the endpoint with custom configuration algorithms by providing an instance of ServerEndpointConfig.Configurator:
                    //
                    // The ServerEndpointConfig.Configurator abstract class offers several methods to configure the endpoint such as providing
                    // custom configuration algorithms,
                    // intercepting the opening handshake, or providing arbitrary methods and algorithms that can be accessed from each endpoint instance
                    // configured with this configurator.
                    //
                    final ServerEndpointConfig.Configurator configurator = new ServerEndpointConfig.Configurator() {

                        // The modifyHandshake method is used to intercept the opening handshake.
                        // ServerEndpointConfig is the endpoint configuration object used to configure this endpoint.
                        // HandshakeRequest provides information about the WebSocket-defined HTTP GET request for the opening handshake.
                        // HandshakeResponse identifies the HTTP handshake response prepared by the container.
                        @Override
                        public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {

                            HttpSession session = (HttpSession) request.getHttpSession();
                            System.out.println("HttpSession id: " + session.getId());
                            System.out.println("HttpSession creation time: " + session.getCreationTime());

                            super.modifyHandshake(sec, request, response);
                        }

                    };

                    final List<Class<? extends Encoder>> encoders = new ArrayList<>();
                    // encoders.add(yourEncoder.class)

                    final List<Class<? extends Decoder>> decoders = new ArrayList<>();

                    add(ServerEndpointConfig.Builder //
                            .create(MyEndpoint.class, "/websocket") //
                            .configurator(configurator) //
                            // The encoders and decoders can be specified on a programmatic server endpoint duringendpoint 
                            // configuration in ServerEndpointConfig.Builder:
                            .encoders(encoders) //
                            .decoders(decoders) //
                            .build());
                }
            };
        }

        @Override
        public Set<Class<?>> getAnnotatedEndpointClasses(Set<Class<?>> set) {
            return Collections.emptySet();
        }
    }

    // =================================================================================================================================================================

    @ClientEndpoint
    public static class MyEndpointTextClient {

        public static CountDownLatch latch = new CountDownLatch(1);

        public static String response;

        @OnOpen
        public void onOpen(Session session) {
            try {
                latch.countDown();

                session.getBasicRemote().sendText("Hello world!");
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }

        @OnMessage
        public void processMessage(final String message) {
            response = message;
            latch.countDown();
        }
    }

    @ClientEndpoint
    public static class MyEndpointBinaryClient {

        public static CountDownLatch latch = new CountDownLatch(1);

        public static byte[] response;

        @OnOpen
        public void onOpen(Session session) {
            try {
                latch.countDown();
                session.getBasicRemote().sendBinary(ByteBuffer.wrap("Hello World!".getBytes()));
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }

        @OnMessage
        public void processMessage(byte[] message) {
            response = message;
            latch.countDown();
        }
    }

    // ==================================================================================================================================================================
    public static class User {

        public String show() {
            return "User is Jane Doe";
        }

    }

    // ==================================================================================================================================================================
    public static void main(String[] args) throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer();) {

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);

            war.addClasses( //
                    MyEndpointConfig.class, //
                    MyEndpoint.class, //
                    MyEndpointBinaryClient.class, //
                    MyEndpointTextClient.class //
            );

            war.addWebInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/beans.xml"));
            // war.addWebResourceFiles(EmbeddedResource.add("src/main/resources/chapter07_webSocket/part01_annotedServerEndpoint/encoder.jsp"));
            // war.addWebResourceFiles(EmbeddedResource.add("src/main/resources/chapter07_webSocket/part01_annotedServerEndpoint/encoder.js"));

            final File warFile = war.exportToFile(APP_FILE_TARGET);

            embeddedJeeServer.start(HTTP_PORT);
            embeddedJeeServer.deploy(EMBEDDED_JEE_TEST_APP_NAME, warFile.getAbsolutePath());

            // --------------------------------------------------------------------------------------------------------------------------------
            // MyEndpointTextClient
            Session session01 = connectToServerWebSockets(MyEndpointTextClient.class, "websocket");
            final MessageHandler.Whole<String> wholeTextClientHandler = new MessageHandler.Whole<String>() {

                @Override
                public void onMessage(String text) {
                    System.out.println(text);
                }
            };

            // session01.addMessageHandler(wholeTextClientHandler); // MessageHandler already registered.

            MyEndpointTextClient.latch.await(3, TimeUnit.SECONDS);
            System.out.println("MyEndpointTextClient: " + MyEndpointTextClient.response);

            // --------------------------------------------------------------------------------------------------------------------------------
            // MyEndpointBinaryClient
            Session session02 = connectToServerWebSockets(MyEndpointBinaryClient.class, "websocket");
            final MessageHandler.Partial<ByteBuffer> partialMessageBinaryHandler = new MessageHandler.Partial<ByteBuffer>() {

                @Override
                public void onMessage(final ByteBuffer binary, final boolean part) {
                    System.out.println(binary);
                    System.out.println(part);
                }
            };

            // session02.addMessageHandler(partialMessageBinaryHandler); MessageHandler already registered.

            MyEndpointBinaryClient.latch.await(3, TimeUnit.SECONDS);
            System.out.println("MyEndpointBinaryClient: " + MyEndpointBinaryClient.response);

            Thread.sleep(5000);

        } catch (final Exception ex) {
            System.out.println(ex);
        }

        downVariables();
    }
}
