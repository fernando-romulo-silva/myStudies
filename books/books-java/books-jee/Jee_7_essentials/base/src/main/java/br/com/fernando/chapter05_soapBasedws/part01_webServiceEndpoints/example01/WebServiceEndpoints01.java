package br.com.fernando.chapter05_soapBasedws.part01_webServiceEndpoints.example01;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;

public class WebServiceEndpoints01 {

    public static void main(final String[] args) throws Exception {

        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer()) {

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addWebInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/beans.xml"));
            war.addClasses(SimpleEndpointInterface.class, SimpleWebService.class);

            final File warFile = war.exportToFile(APP_FILE_TARGET);
            embeddedJeeServer.start(HTTP_PORT);
            embeddedJeeServer.deploy(EMBEDDED_JEE_TEST_APP_NAME, warFile.getAbsolutePath());

            // --------------------------------------------------------------------------------------------------

            final Service simpleService = Service.create( // 
                    new URL("http://localhost:" + HTTP_PORT + "/" + EMBEDDED_JEE_TEST_APP_NAME + "/SimpleWebService?wsdl"), //
                    new QName("http://simplewebservice.com.br", "SimpleWebService"));

            final SimpleEndpointInterface simpleEndpointInterface = simpleService.getPort(SimpleEndpointInterface.class);

            final String response = simpleEndpointInterface.sayHello("Johnson");

            System.out.println(response);
        }

        downVariables();
    }
}
