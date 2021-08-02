package br.com.fernando.chapter05_soapBasedws.part06_handlers;

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

public class Handlers {

    public static void main(String[] args) throws Exception {
        startVariables();

        // Handlers are well-defined extension points that perform additional processing of the request and response messages.
        //
        // They can be easily plugged into the JAX-WS runtime. There are two types of handlers:
        //
        // Logical handler -> look at MyLogicalHandler
        // Protocol handler -> look at MySOAPHandler
        //
        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer()) {
            // ----------------------------------------------------------------------------------------
            // Server
            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addWebInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/beans.xml"));

            // Handlers can be organized in a handler chain.
            // The handlers within a handler chain are invoked each time a message is sent or received.
            //
            // Inbound messages are processed by handlers prior to dispatching a request to the service endpoint or returning a response to the client.
            //
            // Outbound messages are processed by handlers after a request is sent from the client or a response is returned from the service endpoint.

            war.addWebInfFiles(EmbeddedResource.add("classes/br/com/fernando/chapter05_soap_basedws/part06_handlers/handler-chain.xml", "src/main/resources/chapter05_soap_basedws/handler-chain.xml"));

            war.addClasses(//
                    SimpleWebService.class, //
                    SimpleWebServiceInterface.class, //
                    MyLogicalHandler.class, //
                    MySOAPHandler.class);

            final File warFile = war.exportToFile(APP_FILE_TARGET);
            embeddedJeeServer.start(HTTP_PORT);
            embeddedJeeServer.deploy(EMBEDDED_JEE_TEST_APP_NAME, warFile.getAbsolutePath());

            final String defaulQNameUrl = "http://part06_handlers.chapter05_soap_basedws.fernando.com.br/";

            final Service service = Service.create( //
                    new URL("http://localhost:" + HTTP_PORT + "/" + EMBEDDED_JEE_TEST_APP_NAME + "/SimpleWebServiceService?wsdl"), //
                    new QName(defaulQNameUrl, "SimpleWebServiceService"));

            final SimpleWebServiceInterface endpointInterface = service.getPort( //
                    new QName(defaulQNameUrl, "SimpleWebServicePort"), //
                    SimpleWebServiceInterface.class);

            // During runtime, the handler chain is reordered such that logical handlers are executed before the SOAP handlers on an outbound message
            // and SOAP handlers are executed before logical handlers on an inbound message.
            //
            // Send
            // Client -> Logical Handlers -> Soap Handlers -> || internet || -> Soap Handlers -> Logical Handlers -> Endpoint
            //
            // Return
            // Endpoint -> Logical Handlers -> Soap Handlers -> || internet || -> Soap Handlers -> Logical Handlers -> Client
            final String sayHello = endpointInterface.sayHello("Paul");

            System.out.println(sayHello);
        }

        downVariables();

    }
}
