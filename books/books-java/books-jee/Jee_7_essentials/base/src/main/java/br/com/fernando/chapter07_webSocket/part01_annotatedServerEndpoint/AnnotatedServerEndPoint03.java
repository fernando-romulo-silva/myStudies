package br.com.fernando.chapter07_webSocket.part01_annotatedServerEndpoint;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.connectToServerWebSockets;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.ClientEndpoint;
import javax.websocket.ClientEndpointConfig;
import javax.websocket.CloseReason;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import javax.websocket.Extension;
import javax.websocket.HandshakeResponse;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig;

import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;

public class AnnotatedServerEndPoint03 {

    // Encoders provide a way to convert custom Java objects into WebSocket messages and can be specified via the encoders attribute.
    //
    // Decoders provide a way to convert WebSocket messages to custom Java objects and can be specified via the decoders attribute.

    // =================================================================================================================================================================
    @ServerEndpoint( //
            value = "/encoder", //
            // You can specify the encoders and decoders on an annotated endpoint using the encoders and decoders attributes of @ServerEndpoint:
            encoders = { MyMessageEncoder.class }, // 
            decoders = { MyMessageDecoder.class } //
    )
    public static class MyEndpoint {

        @OnMessage
        public MyMessage messageReceived(MyMessage message) {
            System.out.println("MyEndpoint.messageReceived: " + message);

            return message;
        }
    }

    // You can specify the encoders and decoders on a client endpoint using the encoders and  decoders attributes of @ClientEndpoint:
    @ClientEndpoint( //
            encoders = { MyMessageEncoder.class }, // 
            decoders = { MyMessageDecoder.class })
    public static class MyClient {

        public static CountDownLatch latch = new CountDownLatch(3);

        public static MyMessage response;

        @OnOpen
        public void onOpen(Session session) {
            try {
                MyMessage message = new MyMessage("{\"apple\" : \"red\", \"banana\": \"yellow\"}");
                session.getBasicRemote().sendObject(message);
            } catch (IOException | EncodeException ex) {
                Logger.getLogger(MyClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @OnMessage
        public void processMessage(MyMessage message) {
            response = message;
            latch.countDown();
        }

        @OnError
        public void onError(Throwable t) {
            t.printStackTrace();
        }
    }

    // =================================================================================================================================================================
    // Applications can receive and send a payload in raw text and binary format. 
    //
    /// The Decoder.Text<T> interface can be implemented to decode the incoming string payload to the application-specific class:
    public static class MyMessageDecoder implements Decoder.Text<MyMessage> {

        // The decode method decodes the String parameter into an object of type MyMessage
        @Override
        public MyMessage decode(String string) throws DecodeException {
            System.out.println("MyMessageDecoder.decode: " + string);
            // Standard javax.json.* APIs are used to generate the JSON representation from a string:
            MyMessage myMessage = new MyMessage(Json.createReader(new StringReader(string)).readObject());
            return myMessage;
        }

        //  will Decode method returns true if the string can be decoded into the object of type MyMessage.
        @Override
        public boolean willDecode(String string) {
            System.out.println("MyMessageDecoder.willDecode: " + string);
            return true;
        }

        @Override
        public void init(EndpointConfig ec) {
            System.out.println("MyMessageDecoder.init");
        }

        @Override
        public void destroy() {
            System.out.println("MyMessageDecoder.destroy");
        }
    }

    // You canconvert the text payload to an application-specific class by implementing Decoder.Text<T> and Encoder.Text<T>. 
    // You can convert the binary payload to an application-specific class by implementing the Decoder.Binary<T> and Encoder.Binary<T> interfaces.
    public static class MyMessageEncoder implements Encoder.Text<MyMessage> {

        @Override
        public String encode(MyMessage myMessage) throws EncodeException {
            return myMessage.getJsonObject().toString();
        }

        @Override
        public void init(EndpointConfig ec) {
        }

        @Override
        public void destroy() {
        }
    }

    // ==================================================================================================================================================================
    public static class MyMessage {

        // JSON is a typical format for a text payload. Instead of receving the payload as text and then converting it to a JsonObject 
        // (for example, using the APIs defined in the javax.json package), you can define an application-specific class to capture JsonObject:
        private JsonObject jsonObject;

        public MyMessage() {
        }

        public MyMessage(String string) {
            jsonObject = Json.createReader(new StringReader(string)).readObject();
        }

        public MyMessage(JsonObject jsonObject) {
            this.jsonObject = jsonObject;
        }

        public JsonObject getJsonObject() {
            return jsonObject;
        }

        public void setJsonObject(JsonObject jsonObject) {
            this.jsonObject = jsonObject;
        }

        @Override
        public String toString() {
            return jsonObject.toString();
        }
    }

    // =================================================================================================================================================================
    @ClientEndpoint
    public static class MyEndpointClientEmptyJSONArray {

        public static String JSON = "{}";

        public static CountDownLatch latch = new CountDownLatch(1);

        public static String response;

        @OnOpen
        public void onOpen(Session session) {
            try {
                session.getBasicRemote().sendText(JSON);
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
    public static class MyEndpointClientJSONObject {

        public static CountDownLatch latch = new CountDownLatch(1);

        public static String JSON = "{\"apple\" : \"red\", \"banana\": \"yellow\"}";

        public static String response;

        @OnOpen
        public void onOpen(Session session) {
            try {
                session.getBasicRemote().sendText(JSON);
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

    // =================================================================================================================================================================
    // The MyServerWithConfigurator class provides an implementation of ServerEndpointConfig.Configurator.
    // This abstract class offers several methods to configure the end-point, such as providing custom configuration algorithms and
    // intercepting the opening handshake.
    //
    // ServerEndpointConfig is the end-point configuration object used to configure this endpoint.
    // HandshakeRequest provides information about the WebSocket defined HTTP GET request for the opening handshake.
    //
    // This class provides access to data such as the list of HTTP headers that came with the request or the HttpSession that the handshake request was part of.
    // HandshakeResponse identifies the HTTP handshake response prepared by the container
    public static class MyServerWithConfigurator extends ServerEndpointConfig.Configurator {

        @Override
        public void modifyHandshake(final ServerEndpointConfig sec, final HandshakeRequest request, final HandshakeResponse response) {
            super.modifyHandshake(sec, request, response);

            System.out.println("Handshake Request:");
            System.out.println("Serving at: " + request.getRequestURI());
            System.out.println("Handshake Response:");

            for (String h : response.getHeaders().keySet()) {
                for (String k : response.getHeaders().get(h)) {
                    System.out.println("Header: " + h + ", " + k);
                }
            }
        }

        @Override
        public boolean checkOrigin(String originHeaderValue) {
            System.out.println("checkOrigin: " + originHeaderValue);
            return super.checkOrigin(originHeaderValue);
        }

        @Override
        public List<Extension> getNegotiatedExtensions(List<Extension> installed, List<Extension> requested) {
            System.out.println("getNegotiatedExtensions");
            return super.getNegotiatedExtensions(installed, requested);
        }

        @Override
        public String getNegotiatedSubprotocol(List<String> supported, List<String> requested) {
            System.out.println("getNegotiatedSubprotocool");
            return super.getNegotiatedSubprotocol(supported, requested);
        }
    }

    // An optional configurator attribute can be used to specify a custom configuration class for configuring new instances of this endpoint:
    @ServerEndpoint(value = "/websocketConfigurator", configurator = MyServerWithConfigurator.class)
    public static class MyEndpointConfigurator {

        // For endpoints deployed in the Java EE platform, full dependency injection support as described in the CDI specification is available.
        // Field, method, and constructor injection is available in all WebSocket endpoint classes. Interceptors may be enabled for these
        // classes using the standard mechanism:
        @Inject
        private User user;

        // EJBs too.
        @EJB
        private EjbTest ejbTest;

        @OnMessage
        public String receiveMessage(String name) {
            System.out.println("MyEndpointConfigurator.messageReceived: " + name);
            return name;
        }

        // @OnOpen can be used to decorate a method to be called when a new connection from a peer is received
        // The open method is called when a new connection is established with this endpoint.
        // The parameter s provides more details about other end of the connection.
        @OnOpen
        public void open(Session s) {
            System.out.println("MyEndpointConfigurator.open: " + s.getId());
            System.out.println(user.show());
            System.out.println(ejbTest.getMessage());
        }

        // Similarly, @OnClose can be used to decorate a method to be called when a connection is closed from the peer.
        // The close method is called when the connection is terminated.
        // The parameter c provides more details about why a WebSocket connection was closed.
        @OnClose
        public void close(CloseReason c) {
            System.out.println("MyEndpointConfigurator.close: " + c.getReasonPhrase());
        }

        // @OnError may be used to decorate a method to be called when an error is received.
        // The error method is called when there is an error in the connection.
        // The parameter t provides more details about the error.
        @OnError
        public void error(Throwable t) {
            System.out.println("MyEndpointConfigurator.error: " + t.getMessage());
        }
    }

    public static class MyClientWithConfigurator extends ClientEndpointConfig.Configurator {

        @Override
        public void beforeRequest(Map<String, List<String>> headers) {
            System.out.println("beforeRequest:");
            for (String h : headers.keySet()) {
                for (String k : headers.get(h)) {
                    System.out.println("Header: " + h + ", " + k);
                }
            }

        }

        @Override
        public void afterResponse(HandshakeResponse response) {
            System.out.println("afterResponse:");
            for (String h : response.getHeaders().keySet()) {
                for (String k : response.getHeaders().get(h)) {
                    System.out.println("Header: " + h + ", " + k);
                }
            }
        }
    }

    @ClientEndpoint(configurator = MyClientWithConfigurator.class)
    public static class MyClientConfigurator {

        public static CountDownLatch latch = new CountDownLatch(3);

        public static String response;

        @OnOpen
        public void onOpen(Session session) {
            try {
                session.getBasicRemote().sendObject("Now it's a text");
            } catch (IOException | EncodeException ex) {
                Logger.getLogger(MyClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @OnMessage
        public void processMessage(String message) {
            response = message;
            latch.countDown();
        }

        @OnError
        public void onError(Throwable t) {
            t.printStackTrace();
        }
    }

    // ==================================================================================================================================================================
    public static class User {

        public String show() {
            return "User is Jane Doe";
        }
    }

    @Stateless
    public static class EjbTest {

        public String getMessage() {
            return "Message from EJB";
        }
    }

    // ==================================================================================================================================================================
    public static void main(String[] args) throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer();) {

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);

            war.addClasses( //
                    User.class, //
                    EjbTest.class, MyEndpoint.class, //
                    MyClient.class, MyMessageDecoder.class, //
                    MyMessageEncoder.class, //
                    MyEndpointClientEmptyJSONArray.class, //
                    MyEndpointClientJSONObject.class, //
                    MyMessage.class, //
                    MyClientWithConfigurator.class, //
                    MyServerWithConfigurator.class, //
                    MyEndpointConfigurator.class, //
                    MyClientConfigurator.class //
            );

            war.addWebInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/beans.xml"));
            war.addWebResourceFiles(EmbeddedResource.add("src/main/resources/chapter07_webSocket/part01_annotedServerEndpoint/encoder.jsp"));
            war.addWebResourceFiles(EmbeddedResource.add("src/main/resources/chapter07_webSocket/part01_annotedServerEndpoint/encoder.js"));

            final File warFile = war.exportToFile(APP_FILE_TARGET);

            embeddedJeeServer.start(HTTP_PORT);
            embeddedJeeServer.deploy(EMBEDDED_JEE_TEST_APP_NAME, warFile.getAbsolutePath());

            // --------------------------------------------------------------------------------------------------------------------------------
            // MyClient
            connectToServerWebSockets(MyClient.class, "encoder");
            MyClient.latch.await(3, TimeUnit.SECONDS);
            System.out.println("MyClient: " + MyClient.response);

            // --------------------------------------------------------------------------------------------------------------------------------
            // MyEndpointClientEmptyJSONArray
            connectToServerWebSockets(MyEndpointClientEmptyJSONArray.class, "encoder");
            MyEndpointClientEmptyJSONArray.latch.await(3, TimeUnit.SECONDS);
            System.out.println("MyEndpointClientEmptyJSONArray: " + MyEndpointClientEmptyJSONArray.response);

            // --------------------------------------------------------------------------------------------------------------------------------
            // MyEndpointClientJSONObject
            connectToServerWebSockets(MyEndpointClientJSONObject.class, "encoder");
            MyEndpointClientJSONObject.latch.await(3, TimeUnit.SECONDS);
            System.out.println("MyEndpointClientJSONObject: " + MyEndpointClientJSONObject.response);

            // --------------------------------------------------------------------------------------------------------------------------------
            // MyClientConfigurator
            connectToServerWebSockets(MyClientConfigurator.class, "websocketConfigurator");
            MyClientConfigurator.latch.await(3, TimeUnit.SECONDS);
            System.out.println("MyClientConfigurator: " + MyClientConfigurator.response);

            // --------------------------------------------------------------------------------------------------------------------------------
            // MyEndpoint
            System.out.println("Access http://localhost:18080/embeddedJeeContainerTest/encoder.jsp");
            // "ws://localhost:18080/embeddedJeeContainerTest/greet/" + who.value

        } catch (final Exception ex) {
            System.out.println(ex);
        }

        downVariables();
    }
}
