package br.com.fernando.client;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;

import br.com.fernando.server.application.autores.v1.Autores;

/**
 * This class was generated by the JAX-WS RI. JAX-WS RI 2.2.4-b01 Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "autores", targetNamespace = "http://fernando.com.br/server/application/autores/v1", wsdlLocation = "file:/home/fernando/workspace/soa-aplicado-cap-07-1/src/test/resources/webapp/WEB-INF/wsdl/autores.wsdl")
public class AutoresService extends Service {

    private final static URL AUTORES_WSDL_LOCATION;
    private final static WebServiceException AUTORES_EXCEPTION;
    private final static QName AUTORES_QNAME = new QName("http://fernando.com.br/server/application/autores/v1", "autores");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("file:/home/fernando/workspace/soa-aplicado-cap-07-1/src/test/resources/webapp/WEB-INF/wsdl/autores.wsdl");
        } catch (final MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        AUTORES_WSDL_LOCATION = url;
        AUTORES_EXCEPTION = e;
    }

    public AutoresService() {
        super(__getWsdlLocation(), AUTORES_QNAME);
    }

    public AutoresService(final WebServiceFeature... features) {
        super(__getWsdlLocation(), AUTORES_QNAME, features);
    }

    public AutoresService(final URL wsdlLocation) {
        super(wsdlLocation, AUTORES_QNAME);
    }

    public AutoresService(final URL wsdlLocation, final WebServiceFeature... features) {
        super(wsdlLocation, AUTORES_QNAME, features);
    }

    public AutoresService(final URL wsdlLocation, final QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public AutoresService(final URL wsdlLocation, final QName serviceName, final WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return returns Autores
     */
    @WebEndpoint(name = "autoresSOAP")
    public Autores getAutoresSOAP() {
        return super.getPort(new QName("http://fernando.com.br/server/application/autores/v1", "autoresSOAP"), Autores.class);
    }

    /**
     * 
     * @param features
     *            A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy. Supported features not in the <code>features</code> parameter will have their default values.
     * @return returns Autores
     */
    @WebEndpoint(name = "autoresSOAP")
    public Autores getAutoresSOAP(final WebServiceFeature... features) {
        return super.getPort(new QName("http://fernando.com.br/server/application/autores/v1", "autoresSOAP"), Autores.class, features);
    }

    private static URL __getWsdlLocation() {
        if (AUTORES_EXCEPTION != null) {
            throw AUTORES_EXCEPTION;
        }
        return AUTORES_WSDL_LOCATION;
    }

}