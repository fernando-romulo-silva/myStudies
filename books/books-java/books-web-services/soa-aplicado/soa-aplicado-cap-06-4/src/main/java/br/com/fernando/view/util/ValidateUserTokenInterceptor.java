package br.com.fernando.view.util;

import java.util.List;

import javax.wsdl.Message;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.wss4j.common.principal.UsernameTokenPrincipal;
import org.apache.wss4j.dom.WSSecurityEngineResult;
import org.apache.wss4j.dom.handler.WSHandlerConstants;
import org.apache.wss4j.dom.handler.WSHandlerResult;

public class ValidateUserTokenInterceptor extends AbstractPhaseInterceptor<SoapMessage> {

    public ValidateUserTokenInterceptor(final String s) {
        super(s);
    }

    @Override
    public void handleMessage(final SoapMessage message) throws Fault {
        boolean userTokenValidated = false;

        @SuppressWarnings("unchecked")
        final List<Message> result = (List<Message>) message.getContextualProperty(WSHandlerConstants.RECV_RESULTS);

        for (int i = 0; i < result.size(); i++) {
            final WSHandlerResult res = (WSHandlerResult) result.get(i);

            for (int j = 0; j < res.getResults().size(); j++) {

                final WSSecurityEngineResult secRes = res.getResults().get(j);

                final Object obj = secRes.get(WSSecurityEngineResult.TAG_PRINCIPAL);

                System.out.println("" + obj.getClass());

                final UsernameTokenPrincipal principal = (UsernameTokenPrincipal) secRes.get(WSSecurityEngineResult.TAG_PRINCIPAL);

                if (!principal.isPasswordDigest() || principal.getNonce() == null || principal.getPassword() == null || principal.getCreatedTime() == null) {
                    throw new RuntimeException("Invalid Security Header");
                } else {
                    userTokenValidated = true;
                }

            }
        }

        if (!userTokenValidated) {
            throw new RuntimeException("Security processing failed");
        }
    }

}
