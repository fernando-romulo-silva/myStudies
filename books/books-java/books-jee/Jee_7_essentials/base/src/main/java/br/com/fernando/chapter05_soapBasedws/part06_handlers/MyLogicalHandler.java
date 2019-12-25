package br.com.fernando.chapter05_soapBasedws.part06_handlers;

import static br.com.fernando.Util.sourceToXMLString;

import javax.xml.transform.Source;
import javax.xml.ws.LogicalMessage;
import javax.xml.ws.handler.LogicalHandler;
import javax.xml.ws.handler.LogicalMessageContext;
import javax.xml.ws.handler.MessageContext;

// Logical handlers are protocol-agnostic and cannot change any protocol-specific parts of a message (such as headers).
// Logical handlers act only on the payload of the message.
//
// You can write logical handlers by implementing LogicalHandler:
public class MyLogicalHandler implements LogicalHandler<LogicalMessageContext> {

    // The handleMessage method is called for inbound and outbound message processing, 
    // and the handleFault method is invoked for fault processing
    @Override
    public boolean handleMessage(final LogicalMessageContext context) {

        // MessageContext provides a context about the message that is currently being processed by the handler instance. 
        // It provides a predefined set of properties that can be used to communicate among different handlers. 
        // Properties are scoped to APPLICATION or HANDLER.
        final Boolean outboundProperty = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

        if (outboundProperty.booleanValue()) {
            System.out.print("MyLogicalHandler Response:-");
        } else {
            System.out.print("MyLogicalHandler Request:-");
        }

        final LogicalMessage message = context.getMessage();

        try {

            final Source source = message.getPayload();

            // Alternatively, you can get the payload as JAXB objects:
            // Object jaxbPayload = message.getPayload(jaxbContext);

            System.out.println(sourceToXMLString(source));

        } catch (Exception ex) {
            System.err.println(ex);
        }

        return true;
    }

    // The handle  Message and handleFault messages return true to continue further processing, 
    // and false to block processing.
    @Override
    public boolean handleFault(final LogicalMessageContext context) {
        return false;
    }

    @Override
    public void close(final MessageContext context) {
    }
}
