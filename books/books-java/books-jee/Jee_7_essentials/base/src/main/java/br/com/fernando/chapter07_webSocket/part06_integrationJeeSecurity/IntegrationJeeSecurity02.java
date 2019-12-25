package br.com.fernando.chapter07_webSocket.part06_integrationJeeSecurity;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.CERTIFICATE_ALIAS;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTPS_PORT;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.KEY_STORE_PATH_FILE;
import static br.com.fernando.Util.SSL_PASSWORD;
import static br.com.fernando.Util.TRUST_STORE_PATH_FILE;
import static br.com.fernando.Util.createSslContex;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.executeRequestHttps;
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

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
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

public class IntegrationJeeSecurity02 {

    // A transport guarantee of NONE allows unencrypted wss:// connections to the WebSocket.
    // A transport guarantee of CONFIDENTIAL only allows access to the WebSocket over an encrypted (wss://) connection.

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

            war.addWebResourceFiles(EmbeddedResource.add("security.jsp", "src/main/resources/chapter07_webSocket/part06_integrationJeeSecurity/security_https.jsp"));
            war.addWebResourceFiles(EmbeddedResource.add("security.js", "src/main/resources/chapter07_webSocket/part06_integrationJeeSecurity/security_https.js"));

            war.addWebInfFiles(EmbeddedResource.add("web.xml", "src/main/resources/chapter07_webSocket/part06_integrationJeeSecurity/web_https.xml"));
            war.addWebInfFiles(EmbeddedResource.add("glassfish-web.xml", "src/main/resources/chapter02_servlet/part07_security/glassfish-web.xml"));

            final File warFile = war.exportToFile(APP_FILE_TARGET);

            final Path fileRealmPath = Paths.get(IntegrationJeeSecurity02.class.getResource("/chapter07_webSocket/part06_integrationJeeSecurity/keyfile").toURI());
            final MyEmbeddedJeeRealmFile realm = new MyEmbeddedJeeRealmFileBuilder("myRealm", fileRealmPath) //
                    .withDigestAlgorithm("SSHA256") // Default
                    .withEncoding("Hex") // Default
                    .build();

            embeddedJeeServer.addFileRealm(realm);
            embeddedJeeServer.start(HTTP_PORT, HTTPS_PORT, CERTIFICATE_ALIAS, SSL_PASSWORD, KEY_STORE_PATH_FILE, SSL_PASSWORD, TRUST_STORE_PATH_FILE);
            embeddedJeeServer.deploy(EMBEDDED_JEE_TEST_APP_NAME, warFile.getAbsolutePath());

            // -------------------------------------------------------------------------------------------------------------------------------

            final String url01 = "https://localhost:" + HTTPS_PORT + "/" + EMBEDDED_JEE_TEST_APP_NAME + "/security.jsp";
            executeRequestHttps("user01", "changeit", HttpGet.class, url01); // user01 ROLE01 // OK

            // ------------------------------------------------------------------------------------------------------------------------------
            // https://blogs.oracle.com/pavelbucek/securing-websocket-applications-on-glassfish
            // Unfortunately, there isn't a default way to connect to web socket client with https (wss)

            System.setProperty("javax.net.ssl.trustStore", TRUST_STORE_PATH_FILE);
            System.setProperty("javax.net.ssl.trustStorePassword", SSL_PASSWORD);

            System.setProperty("javax.net.ssl.keyStore", KEY_STORE_PATH_FILE);
            System.setProperty("javax.net.ssl.keyStorePassword", SSL_PASSWORD);

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

            SSLContext sslContex = createSslContex(KEY_STORE_PATH_FILE, TRUST_STORE_PATH_FILE, SSL_PASSWORD);
            SSLSocketFactory sslFactory = sslContex.getSocketFactory();
            sslFactory.createSocket();

            final ClientEndpointConfig clientConfig = ClientEndpointConfig.Builder.create().configurator(configurator).build();
            clientConfig.getUserProperties().put("org.apache.websocket.SSL_CONTEXT", sslContex); // tomcat way

            final String uri = "wss://localhost:" + HTTPS_PORT + "/" + EMBEDDED_JEE_TEST_APP_NAME + "/websocket";

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
