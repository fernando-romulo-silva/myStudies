package br.com.fernando.view.util;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.wss4j.common.ext.WSPasswordCallback;

public class ServerPasswordCallbackHandler implements CallbackHandler {

    @Override
    public void handle(final Callback[] callbacks) throws IOException, UnsupportedCallbackException {

        final WSPasswordCallback pc = (WSPasswordCallback) callbacks[0];

        if ("soa-aplicado".equals(pc.getIdentifier())) {
            pc.setPassword("soa-aplicado");
        }
    }
}
