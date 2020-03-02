package br.com.fernando.client;

import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.apache.ws.security.WSSecurityException;
import org.apache.ws.security.message.WSSecHeader;
import org.apache.ws.security.message.WSSecTimestamp;
import org.apache.ws.security.message.WSSecUsernameToken;

public class WSSecurityHandler implements SOAPHandler<SOAPMessageContext> {

    private final String username;
    private final String password;

    private final boolean encoded;

    public WSSecurityHandler(final String username, final String password) {
        this(username, password, false);
    }

    public WSSecurityHandler(final String username, final String password, final boolean encoded) {
        this.username = username;
        this.password = password;
        this.encoded = encoded;
    }

    @Override
    public void close(final MessageContext context) {
    }

    @Override
    public boolean handleFault(final SOAPMessageContext context) {
        return true;
    }

    @Override
    public boolean handleMessage(final SOAPMessageContext context) {
        final Boolean outbound = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

        if (outbound) {
            try {
                final SOAPMessage message = context.getMessage();
                final WSSecHeader header = new WSSecHeader();
                header.insertSecurityHeader(message.getSOAPBody().getOwnerDocument());

                final WSSecUsernameToken usernameToken = new WSSecUsernameToken();
                usernameToken.setUserInfo(username, password);
                usernameToken.setPasswordsAreEncoded(encoded);

                usernameToken.prepare(message.getSOAPBody().getOwnerDocument());
                usernameToken.appendToHeader(header);

                final WSSecTimestamp timestamp = new WSSecTimestamp();
                timestamp.build(message.getSOAPBody().getOwnerDocument(), header);
            } catch (WSSecurityException | SOAPException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    @Override
    public Set<QName> getHeaders() {
        return null;
    }

}
