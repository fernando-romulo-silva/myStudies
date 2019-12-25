package br.com.fernando.chapter05_soapBasedws.part01_webServiceEndpoints.example02;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.net.URL;
import java.util.Arrays;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;

public class WebServiceEndpoints02 {

    public static void main(final String[] args) throws Exception {

        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer()) {

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addWebInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/beans.xml"));
            war.addClasses(ShoppingCartEndpointInterface.class, ShoppingCartWS.class, //
                    NamesListElement.class, NameCreator.class, //
                    NameCreatorEndpoint.class, Item.class, //
                    InvalidNameException.class);

            final File warFile = war.exportToFile(APP_FILE_TARGET);
            embeddedJeeServer.start(HTTP_PORT);
            embeddedJeeServer.deploy(EMBEDDED_JEE_TEST_APP_NAME, warFile.getAbsolutePath());

            // --------------------------------------------------------------------------------------------------

            final String defaulQNameUrl = "http://example02.part01_webServiceEndpoints.chapter05_soap_basedws.fernando.com.br/";

            final Service shoppingCartService = Service.create( //
                    new URL("http://localhost:" + HTTP_PORT + "/" + EMBEDDED_JEE_TEST_APP_NAME + "/ShoppingCartWSService?wsdl"), //
                    new QName(defaulQNameUrl, "ShoppingCartWSService"));

            final ShoppingCartEndpointInterface shoppingCartEndpointInterface = shoppingCartService.getPort( //
                    new QName(defaulQNameUrl, "ShoppingCartWSPort"), //
                    ShoppingCartEndpointInterface.class);

            shoppingCartEndpointInterface.purchase(Arrays.asList( //
                    new Item("prod01"), //
                    new Item("prod02") //
            ));

            shoppingCartEndpointInterface.doSomething();

            // ----------------------------------------------------------------------------------------------------

            final Service nameCreatorService = Service.create( //
                    new URL("http://localhost:" + HTTP_PORT + "/" + EMBEDDED_JEE_TEST_APP_NAME + "/NameCreatorService?wsdl"), //
                    new QName(defaulQNameUrl, "NameCreatorService"));

            NameCreatorEndpoint nameCreatorEndpoint = nameCreatorService.getPort( //
                    new QName(defaulQNameUrl, "NameCreatorPort"), //
                    NameCreatorEndpoint.class);

            System.out.println(nameCreatorEndpoint.createName());

            System.out.println(nameCreatorEndpoint.createNames());
        }

        downVariables();
    }

}
