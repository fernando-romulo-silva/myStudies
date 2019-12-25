package br.com.fernando.chapter05_soapBasedws.part02_providerBasedDynamicEndpoint;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.sourceToXMLString;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.io.StringReader;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;

import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;

public class ProviderBasedDynamicEndpoint {

    public static void main(final String[] args) throws Exception {

        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer()) {

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addWebInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/beans.xml"));
            war.addWebInfFiles(EmbeddedResource.add("wsdl/SimpleClientService.wsdl", "src/main/resources/chapter05_soap_basedws/SimpleClientService.wsdl"));
            war.addClasses(MyProvider.class);

            final File warFile = war.exportToFile(APP_FILE_TARGET);
            embeddedJeeServer.start(HTTP_PORT);
            embeddedJeeServer.deploy(EMBEDDED_JEE_TEST_APP_NAME, warFile.getAbsolutePath());

            // --------------------------------------------------------------------------------------------------
            //
            final String name = "Joao";
            final String request = "<ns1:sayHello xmlns:ns1=\"http://simplewebservice.com.br\"><arg0>" + name + "</arg0></ns1:sayHello>";

            final QName portQName = new QName("http://simplewebservice.com.br", "SimplePort"); // port name

            final Service service = Service.create( // 
                    new URL("http://localhost:" + HTTP_PORT + "/" + EMBEDDED_JEE_TEST_APP_NAME + "/SimpleWebService?wsdl"), // wsdlLocation
                    new QName("http://simplewebservice.com.br", "SimpleWebService")); // serviceQName

            // -----------------------------------------------------------------------------------------

            final Dispatch<Source> dispatch = service.createDispatch(portQName, Source.class, Service.Mode.PAYLOAD);

            final Source result = dispatch.invoke(new StreamSource(new StringReader(request)));
            final String xmlResult = sourceToXMLString(result);
            System.out.println("Received xml response: " + xmlResult);
        }

        downVariables();
    }
}
