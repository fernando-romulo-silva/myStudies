package br.com.fernando.client;

import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

// Para observar qdo o servidor enivar a resposta, precisamos de um handler.

public class AddressingHandler implements SOAPHandler<SOAPMessageContext> {

    private QName responseQName;
    private final String enderecoResposta;

    public AddressingHandler(final String enderecoResposta) {
        this.enderecoResposta = enderecoResposta;
    }

    @Override
    public boolean handleMessage(final SOAPMessageContext context) {

        try {
            if (context.get(SOAPMessageContext.MESSAGE_OUTBOUND_PROPERTY).equals(Boolean.TRUE)) {
                trataRequisicao(context);
            } else {
                trataResposta(context);
            }
        } catch (final SOAPException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private void trataResposta(final SOAPMessageContext context) throws SOAPException {
        final SOAPMessage soapMessage = context.getMessage();
        soapMessage.getSOAPBody().addChildElement(this.responseQName);

    }

    private void trataRequisicao(final SOAPMessageContext context) throws SOAPException {
        final SOAPMessage soapMessage = context.getMessage();
        final NodeList nodeList = soapMessage.getSOAPHeader().getElementsByTagName("Address");
        final Node node = nodeList.item(0);
        node.setTextContent(enderecoResposta);

        final Node requestNode = soapMessage.getSOAPBody().getFirstChild();
        final String namespace = requestNode.getNamespaceURI();
        final String nodeName = requestNode.getLocalName() + "Response";

        final QName qName = new QName(namespace, nodeName);
        this.responseQName = qName;

    }

    @Override
    public boolean handleFault(final SOAPMessageContext context) {
        return true;
    }

    @Override
    public void close(final MessageContext context) {

    }

    @Override
    public Set<QName> getHeaders() {
        return null;
    }

}
