package br.com.fernando.view.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.saaj.SAAJInInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.wss4j.dom.WSConstants;
import org.apache.wss4j.dom.handler.WSHandlerConstants;

public class WSSecurityPhaseInterceptor extends AbstractPhaseInterceptor<SoapMessage> {

    public WSSecurityPhaseInterceptor() {
        super(Phase.PRE_PROTOCOL);
    }

    public WSSecurityPhaseInterceptor(final String s) {
        super(Phase.PRE_PROTOCOL);
    }

    @Override
    public void handleMessage(final SoapMessage message) throws Fault {

        final Map<String, Object> props = new HashMap<String, Object>();
        props.put(WSHandlerConstants.ACTION, WSHandlerConstants.USERNAME_TOKEN);
        props.put(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_DIGEST);
        props.put(WSHandlerConstants.PW_CALLBACK_REF, new ServerPasswordCallbackHandler());

        final WSS4JInInterceptor wss4jInHandler = new WSS4JInInterceptor(props);
        final ValidateUserTokenInterceptor userTokenInterceptor = new ValidateUserTokenInterceptor(Phase.POST_PROTOCOL);

        message.getInterceptorChain().add(wss4jInHandler);
        message.getInterceptorChain().add(new SAAJInInterceptor());
        message.getInterceptorChain().add(userTokenInterceptor);
    }
}
