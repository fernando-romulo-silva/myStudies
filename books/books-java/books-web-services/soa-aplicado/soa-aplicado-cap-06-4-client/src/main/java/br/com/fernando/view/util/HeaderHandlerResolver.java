package br.com.fernando.view.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TimeZone;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.PortInfo;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.apache.commons.codec.binary.Base64;

public class HeaderHandlerResolver implements HandlerResolver {

    @SuppressWarnings("rawtypes")
    @Override
    public List<Handler> getHandlerChain(final PortInfo portInfo) {
        final List<Handler> handlerChain = new ArrayList<>();

        final HeaderHandler hh = new HeaderHandler();

        handlerChain.add(hh);

        return handlerChain;
    }

    private static class HeaderHandler implements SOAPHandler<SOAPMessageContext> {

        private String calculatePasswordDigest(final String nonce, final String created, final String password) {
            String encoded = null;
            try {
                final String pass = hexEncode(nonce) + created + password;
                final MessageDigest md = MessageDigest.getInstance("SHA1");
                md.update(pass.getBytes());
                final byte[] encodedPassword = md.digest();
                encoded = Base64.encodeBase64String(encodedPassword);
            } catch (final NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }

            return encoded;
        }

        private String hexEncode(final String in) {

            final StringBuilder sb = new StringBuilder("");

            for (int i = 0; i < in.length() - 2 + 1; i = i + 2) {
                final int c = Integer.parseInt(in.substring(i, i + 2), 16);
                final char chr = (char) c;
                sb.append(chr);
            }

            return sb.toString();
        }

        private String getFormatedTime() {
            final Date date = new Date();

            final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

            return dateFormat.format(date);
        }

        @Override
        public boolean handleMessage(final SOAPMessageContext smc) {

            final Boolean outboundProperty = (Boolean) smc.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

            if (outboundProperty.booleanValue()) {

                final SOAPMessage message = smc.getMessage();

                try {

                    final Random generator = new Random();

                    final String nonceString = String.valueOf(generator.nextInt(99999999));
                    final String userString = "soa-aplicado";
                    final String passwordString = "soa-aplicado";
                    final String createdString = getFormatedTime();

                    final String passwordDigestString = calculatePasswordDigest(nonceString, createdString, passwordString);

                    final SOAPEnvelope envelope = smc.getMessage().getSOAPPart().getEnvelope();
                    final SOAPHeader header = envelope.getHeader() == null ? envelope.addHeader() : envelope.getHeader();

                    // Security
                    final SOAPHeaderElement security = (SOAPHeaderElement) header.addChildElement("Security", "wsse", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
                    security.setAttribute("xmlns:wsu", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
                    security.setMustUnderstand(true);

                    // User Token
                    final SOAPElement usernameToken = security.addChildElement("UsernameToken", "wsse");
                    usernameToken.setAttribute("wsu:Id", "UsernameToken-1");
                    final SOAPElement username = usernameToken.addChildElement("Username", "wsse");
                    username.addTextNode(userString);

                    // Password
                    final SOAPElement password = usernameToken.addChildElement("Password", "wsse");
                    password.setAttribute("Type", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest");
                    password.addTextNode(passwordDigestString);

                    // Nonce
                    final SOAPElement nonce = usernameToken.addChildElement("Nonce", "wsse");
                    nonce.setAttribute("EncodingType", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary");
                    nonce.addTextNode(Base64.encodeBase64String(hexEncode(nonceString).getBytes()));

                    final SOAPElement created = usernameToken.addChildElement("Created", "wsu");
                    created.addTextNode(createdString);

                    // Print out the outbound SOAP message to System.out
                    // message.writeTo(System.out);
                    // System.out.println("");

                } catch (final Exception e) {
                    e.printStackTrace();
                }

            } else {
                try {

                    // This handler does nothing with the response from the Web Service so
                    // we just print out the SOAP message.
                    // final SOAPMessage message = smc.getMessage();
                    // message.writeTo(System.out);
                    // System.out.println("");

                } catch (final Exception ex) {
                    ex.printStackTrace();
                }
            }

            return outboundProperty;
        }

        @Override
        public boolean handleFault(final SOAPMessageContext context) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public void close(final MessageContext context) {
            // TODO Auto-generated method stub
        }

        @Override
        public Set<QName> getHeaders() {
            // TODO Auto-generated method stub
            return null;
        }

    }

}