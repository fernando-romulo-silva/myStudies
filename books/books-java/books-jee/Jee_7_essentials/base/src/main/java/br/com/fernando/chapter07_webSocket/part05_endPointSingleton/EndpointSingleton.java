package br.com.fernando.chapter07_webSocket.part05_endPointSingleton;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;

import javax.websocket.OnMessage;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;

public class EndpointSingleton {

    @ServerEndpoint(value = "/websocket", configurator = MyConfigurator.class)
    public static class MyEndpoint {

        String newValue = "";

        /**
         * singleton instance of endpoint ensures that string concatenation would work
         */
        @OnMessage
        public String concat(String value) {
            this.newValue += value;

            return newValue;
        }
    }

    public static class MyConfigurator extends ServerEndpointConfig.Configurator {

        private static final MyEndpoint ENDPOINT = new MyEndpoint();

        @SuppressWarnings("unchecked")
        @Override
        public <T> T getEndpointInstance(Class<T> endpointClass) throws InstantiationException {
            if (MyEndpoint.class.equals(endpointClass)) {
                return (T) ENDPOINT;
            } else {
                throw new InstantiationException();
            }
        }
    }

    // ==================================================================================================================================================================
    public static void main(String[] args) throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer();) {

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);

            war.addClasses( //
                    MyEndpoint.class, //
                    MyConfigurator.class);

            war.addWebResourceFiles(EmbeddedResource.add("src/main/resources/chapter07_webSocket/part05_endpointSingleton/endpointSingleton.jsp"));
            war.addWebResourceFiles(EmbeddedResource.add("src/main/resources/chapter07_webSocket/part05_endpointSingleton/endpointSingleton.js"));

            final File warFile = war.exportToFile(APP_FILE_TARGET);

            embeddedJeeServer.start(HTTP_PORT);
            embeddedJeeServer.deploy(EMBEDDED_JEE_TEST_APP_NAME, warFile.getAbsolutePath());

            // -----------------------------------------------------------------------------------------------------------------------

            final HttpClient httpClient = HttpClientBuilder.create().build();

            final HttpResponse response01 = httpClient.execute(new HttpGet("http://localhost:" + HTTP_PORT + "/" + EMBEDDED_JEE_TEST_APP_NAME + "/endpointSingleton.jsp"));
            System.out.println(response01);
        } catch (final Exception ex) {
            System.out.println(ex);
        }

        downVariables();
    }

}
