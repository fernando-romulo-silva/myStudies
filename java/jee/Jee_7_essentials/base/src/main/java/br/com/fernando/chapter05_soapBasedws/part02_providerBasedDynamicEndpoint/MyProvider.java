package br.com.fernando.chapter05_soapBasedws.part02_providerBasedDynamicEndpoint;

import java.io.ByteArrayInputStream;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.Provider;
import javax.xml.ws.Service;
import javax.xml.ws.ServiceMode;
import javax.xml.ws.WebServiceProvider;

import org.w3c.dom.Node;

// By default, only the message payload (i.e., the SOAP body in the case of the SOAP protocol) is received at the endpoint and sent in a response.
// The @ServiceMode annotation specifies whether the Provider instance receives entire messages or message payloads.
@ServiceMode(value = Service.Mode.PAYLOAD)
//
// Standard JWS annotation that configures the Provider-based web service.
// @WebServiceProvider is used to associate the class with a wsdl:service and a wsdl:port element in the WSDL document.
@WebServiceProvider( //
        portName = "SimplePort", //
        serviceName = "SimpleWebService", // 
        targetNamespace = "http://simplewebservice.com.br", //
        wsdlLocation = "WEB-INF/wsdl/SimpleClientService.wsdl" //
)
// AsyncProvider
//
// MESSAGE: SOAP message is received and sent from the endpoint.
public class MyProvider implements Provider<Source> {

    // A Provider -based endpoint provides a dynamic alternative to the SEI-based endpoint.
    // Instead of just the mapped Java types, the complete protocol message or protocol message payload is available as Source, DataSource, or SOAPMessage at the endpoint. 
    // The response message also needs to be prepared using these APIs.

    // The endpoint needs to implement the Provider<Source>, Provider<SOAPMessage>, or Provider<DataSource> interface
    //
    // In this code, the SOAP body payload is available as a Source. 

    @Override
    public Source invoke(final Source source) {
        try {
            final DOMResult dom = new DOMResult();
            final Transformer trans = TransformerFactory.newInstance().newTransformer();
            trans.transform(source, dom);

            // Get the node of operation 
            final Node node = dom.getNode();
            // Get the operation name node.
            final Node root = node.getFirstChild();
            // Get the parameter node.
            final Node first = root.getFirstChild();
            final String input = first.getFirstChild().getNodeValue();
            // Get the operation name.
            final String op = root.getLocalName();

            System.out.println("Operation: " + op + " Parameter: " + input);

            if ("invokeNoTransaction".equals(op)) {
                return sendSource(input);
            } else {
                return sendSource2(input);
            }

        } catch (final Exception e) {
            throw new RuntimeException("Error in provider endpoint", e);
        }
    }

    private Source sendSource(final String input) {

        final String body = "<ns:invokeNoTransactionResponse xmlns:ns=\"http://simplewebservice.com.br/\"><return>" //
                + "Hello " + input + "</return></ns:invokeNoTransactionResponse>";

        final Source source = new StreamSource(new ByteArrayInputStream(body.getBytes()));
        return source;
    }

    private Source sendSource2(final String input) {
        final String body = "<ns:invokeTransactionResponse xmlns:ns=\"http://simplewebservice.com.br/\"><return>" //
                + "Hello " + input + "</return></ns:invokeTransactionResponse>";

        final Source source = new StreamSource(new ByteArrayInputStream(body.getBytes()));
        return source;
    }

}
