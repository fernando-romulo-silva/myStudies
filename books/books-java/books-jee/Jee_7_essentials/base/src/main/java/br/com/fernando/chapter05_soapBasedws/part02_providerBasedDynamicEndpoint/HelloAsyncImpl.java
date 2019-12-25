package br.com.fernando.chapter05_soapBasedws.part02_providerBasedDynamicEndpoint;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceProvider;

import com.sun.xml.ws.api.server.AsyncProvider;
import com.sun.xml.ws.api.server.AsyncProviderCallback;

// https://docs.oracle.com/middleware/1213/wls/WSGET/jax-ws-provider.htm#WSGET583
// https://javaee.github.io/metro-jax-ws/doc/user-guide/ch03.html
@WebServiceProvider( //
        wsdlLocation = "WEB-INF/wsdl/hello_literal.wsdl", //
        targetNamespace = "urn:test", //
        serviceName = "Hello") //
public class HelloAsyncImpl implements AsyncProvider<Source> {

    private static final JAXBContext jaxbContext = createJAXBContext();

    private int bodyIndex;

    public JAXBContext getJAXBContext() {
        return jaxbContext;
    }

    private static JAXBContext createJAXBContext() {
        try {
            return JAXBContext.newInstance(ObjectFactory.class);
        } catch (final JAXBException e) {
            throw new WebServiceException(e.getMessage(), e);
        }
    }

    private Source sendSource() {
        System.out.println("**** sendSource ******");

        String[] body = { //
                "<HelloResponse xmlns=\"urn:test:types\"><argument xmlns=\"\">foo</argument><extra xmlns=\"\">bar</extra></HelloResponse>", //
                "<ans1:HelloResponse xmlns:ans1=\"urn:test:types\"> <argument>foo</argument> <extra>bar</extra></ans1:HelloResponse>", //
        };

        int i = (++bodyIndex) % body.length;
        return new StreamSource(new ByteArrayInputStream(body[i].getBytes()));
    }

    private Hello_Type recvBean(final Source source) throws Exception {
        System.out.println("**** recvBean ******");
        return (Hello_Type) jaxbContext.createUnmarshaller().unmarshal(source);
    }

    private Source sendBean() throws Exception {
        System.out.println("**** sendBean ******");
        final HelloResponse resp = new HelloResponse();
        // resp.setArgument("foo");
        // resp.setExtra("bar");
        final ByteArrayOutputStream bout = new ByteArrayOutputStream();
        jaxbContext.createMarshaller().marshal(resp, bout);
        return new StreamSource(new ByteArrayInputStream(bout.toByteArray()));
    }

    @Override
    public void invoke(final Source source, final AsyncProviderCallback<Source> cbak, final WebServiceContext ctxt) {
        System.out.println("**** Received in AsyncProvider Impl ******");
        try {
            final Hello_Type hello = recvBean(source);
            final String arg = hello.getArgument();
            if (arg.equals("sync")) {
                final String extra = hello.getExtra();
                if (extra.equals("source")) {
                    cbak.send(sendSource());
                } else if (extra.equals("bean")) {
                    cbak.send(sendBean());
                } else {
                    throw new WebServiceException("Expected extra = (source|bean|fault), Got=" + extra);
                }
            } else if (arg.equals("async")) {
                new Thread(new RequestHandler(cbak, hello)).start();
            } else {
                throw new WebServiceException("Expected Argument = (sync|async), Got=" + arg);
            }
        } catch (final Exception e) {
            throw new WebServiceException("Endpoint failed", e);
        }
    }

    private class RequestHandler implements Runnable {

        final AsyncProviderCallback<Source> cbak;

        final Hello_Type hello;

        public RequestHandler(AsyncProviderCallback<Source> cbak, Hello_Type hello) {
            this.cbak = cbak;
            this.hello = hello;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ie) {
                cbak.sendError(new WebServiceException("Interrupted..."));
                return;
            }
            try {
                String extra = hello.getExtra();
                if (extra.equals("source")) {
                    cbak.send(sendSource());
                } else if (extra.equals("bean")) {
                    cbak.send(sendBean());
                } else {
                    cbak.sendError(new WebServiceException("Expected extra = (source|bean|fault), Got=" + extra));
                }
            } catch (Exception e) {
                cbak.sendError(new WebServiceException(e));
            }
        }
    }

    public static class Hello_Type {

        public String getExtra() {
            // TODO Auto-generated method stub
            return null;
        }

        public String getArgument() {
            // TODO Auto-generated method stub
            return null;
        }

    }

    public static class HelloResponse {

    }

    public class ObjectFactory {

    }

}
