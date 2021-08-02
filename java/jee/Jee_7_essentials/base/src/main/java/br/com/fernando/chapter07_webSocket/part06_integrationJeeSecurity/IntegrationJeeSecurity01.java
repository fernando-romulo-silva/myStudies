package br.com.fernando.chapter07_webSocket.part06_integrationJeeSecurity;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.executeRequestHttp;
import static br.com.fernando.Util.startVariables;
import static java.util.Arrays.asList;
import static javax.xml.bind.DatatypeConverter.printBase64Binary;

import java.io.File;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.websocket.ClientEndpoint;
import javax.websocket.ClientEndpointConfig;
import javax.websocket.ContainerProvider;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import javax.websocket.server.ServerEndpoint;
import javax.xml.bind.DatatypeConverter;

import org.apache.http.client.methods.HttpGet;
import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.jeecontainer.resources.MyEmbeddedJeeRealmFile;
import org.myembedded.jeecontainer.resources.MyEmbeddedJeeRealmFile.MyEmbeddedJeeRealmFileBuilder;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;

public class IntegrationJeeSecurity01 {

    // A WebSocket mapped to a given ws:// URI is protected in the deployment descriptor with a listing to an http:// URI with
    // same hostname, port, and path since this is the URL of its opening handshake. The authentication and authorization of the
    // WebSocket endpoint builds on the servlet-defined security mechanism.
    //
    // A WebSocket that requires authentication must rely on the opening handshake request that seeks to initiate a connection to be previously authenticated.
    // Typically, this will be performed by an HTTP authentication (perhaps basic or form-based) in the web application containing the WebSocket prior to the
    // opening handshake to the WebSocket.
    //
    // Accordingly, WebSocket developers may assign an authentication scheme, user-rolebased access, and a transport guarantee to their WebSocket endpoints.
    //
    // If a client sends an unauthenticated opening handshake request for a WebSocket that is protected by the security mechanism, a 401 (Unauthorized) response
    // to the opening handshake request is returned and the WebSocket connection is not initialized.
    //
    //
    @ServerEndpoint(value = "/websocket")
    public static class MyEndpoint {

        @OnMessage
        public String echoText(final String text) {
            System.out.println("The message: " + text);
            return "Echo " + text;
        }

        @OnOpen
        public void onOpen(final Session session) throws Exception {
            System.out.println("Hello " + session.getUserPrincipal().getName());
        }
    }

    @ClientEndpoint(configurator = AuthorizationConfigurator.class)
    public static class MyClient {

        @OnMessage
        public void processMessage(String message, Session session) {
            System.out.println("Received message in client: " + message);
        }
    }

    // As you can see the issue with the annotation solution is that you can't easily pass parameters to the Configurator where it would be quite easy to
    // do it with the programmatic API (and create a generic AuthorizationHeaderConfigurator).
    public class AuthorizationConfigurator extends ClientEndpointConfig.Configurator {

        @Override
        public void beforeRequest(Map<String, List<String>> headers) {
            headers.put("Authorization", asList("Basic " + printBase64Binary("user01:changeit".getBytes())));
        }
    }

    // ==================================================================================================================================================================
    public static void main(String[] args) throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer();) {

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addClasses( //
                    MyClient.class, //
                    MyEndpoint.class //
            );

            war.addWebResourceFiles(EmbeddedResource.add("src/main/resources/chapter07_webSocket/part06_integrationJeeSecurity/security.jsp"));
            war.addWebResourceFiles(EmbeddedResource.add("src/main/resources/chapter07_webSocket/part06_integrationJeeSecurity/security.js"));

            war.addWebInfFiles(EmbeddedResource.add("web.xml", "src/main/resources/chapter07_webSocket/part06_integrationJeeSecurity/web.xml"));
            war.addWebInfFiles(EmbeddedResource.add("glassfish-web.xml", "src/main/resources/chapter02_servlet/part07_security/glassfish-web.xml"));

            final File warFile = war.exportToFile(APP_FILE_TARGET);

            final Path fileRealmPath = Paths.get(IntegrationJeeSecurity02.class.getResource("/chapter07_webSocket/part06_integrationJeeSecurity/keyfile").toURI());
            final MyEmbeddedJeeRealmFile realm = new MyEmbeddedJeeRealmFileBuilder("myRealm", fileRealmPath) //
                    .withDigestAlgorithm("SSHA256") // Default
                    .withEncoding("Hex") // Default
                    .build();

            embeddedJeeServer.addFileRealm(realm);
            embeddedJeeServer.start(HTTP_PORT);
            embeddedJeeServer.deploy(EMBEDDED_JEE_TEST_APP_NAME, warFile.getAbsolutePath());

            // -------------------------------------------------------------------------------------------------------------------------------

            final String url01 = "http://localhost:" + HTTP_PORT + "/" + EMBEDDED_JEE_TEST_APP_NAME + "/security.jsp";
            executeRequestHttp("user01", "changeit", HttpGet.class, url01); // user01 ROLE01 // OK

            // -------------------------------------------------------------------------------------------------------------------------------
            final Endpoint endpoint = new Endpoint() {

                @Override
                public void onOpen(final Session session, final EndpointConfig config) {

                    session.addMessageHandler(new MessageHandler.Whole<String>() {

                        @Override
                        public void onMessage(final String content) {
                            System.out.println("Content: " + content);
                        }
                    });
                }
            };

            final ClientEndpointConfig.Configurator configurator = new ClientEndpointConfig.Configurator() {

                @Override
                public void beforeRequest(final Map<String, List<String>> headers) {
                    headers.put("Authorization", Arrays.asList("Basic " + DatatypeConverter.printBase64Binary("user01:changeit".getBytes())));
                }
            };

            final ClientEndpointConfig clientConfig = ClientEndpointConfig.Builder.create().configurator(configurator).build();

            final String uri = "ws://localhost:" + HTTP_PORT + "/" + EMBEDDED_JEE_TEST_APP_NAME + "/websocket";

            final WebSocketContainer container = ContainerProvider.getWebSocketContainer();

            final Session session = container.connectToServer(endpoint, clientConfig, URI.create(uri));
            session.getBasicRemote().sendText("This is a Message");

            session.close();
        } catch (final Exception ex) {
            System.out.println(ex);
        }

        downVariables();
    }

}
