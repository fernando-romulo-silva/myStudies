package br.com.fernando.chapter05_soapBasedws.part06_handlers;

import java.util.Iterator;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.Node;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import javax.xml.ws.soap.SOAPFaultException;

// Protocol handlers are specific to a protocol and may access or change the protocolspecific aspects of a message.
public class MySOAPHandler implements SOAPHandler<SOAPMessageContext> {

    private String SERVICE_CONSUMER = "serviceconsumer";

    // In this code, the handler has implemented the handleMessage, handleFault, close, and getHeaders methods. 
    // SOAP handlers are generally used to process SOAP-specific information, such as SOAP headers. 
    // The getHeaders method returns the set of SOAP headers processed by this handler instance.
    @Override
    public boolean handleMessage(SOAPMessageContext context) {

        Boolean outboundProperty = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

        // incoming request
        if (!outboundProperty) {
            System.out.println("MySOAPHandler Client Request...");
            checkMessage(context);
        } else {
            System.out.println("MySOAPHandler Client Response...");
        }
        return true;
    }

    // Check ConsumerApplication
    private boolean checkMessage(final SOAPMessageContext context) {
        try {
            final SOAPMessage soapMessage = context.getMessage();
            final SOAPEnvelope soapEnvelope = soapMessage.getSOAPPart().getEnvelope();
            final SOAPHeader soapHeader = soapEnvelope.getHeader();

            final Iterator<?> headers = soapHeader.extractHeaderElements(SOAPConstants.URI_SOAP_ACTOR_NEXT);

            if (headers != null && headers.hasNext()) {
                Node node = (Node) headers.next();

                if (node != null) {
                    if (!SERVICE_CONSUMER.equals(node.getNodeName())) {
                        System.out.println("Missing ServiceConsumer Header");
                        SOAPBody soapBody = soapMessage.getSOAPPart().getEnvelope().getBody();
                        SOAPFault soapFault = soapBody.addFault();
                        soapFault.setFaultString("Invalid Service Consumer..!!");

                        throw new SOAPFaultException(soapFault);
                    }
                }
            }

        } catch (SOAPException e) {
            System.err.println(e);
        }
        return true;

    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        return false;
    }

    @Override
    public void close(MessageContext context) {

    }

    @Override
    public Set<QName> getHeaders() {
        return null;
    }
}
