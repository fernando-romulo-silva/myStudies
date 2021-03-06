
package br.com.fernando.client;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;

import br.com.fernando.server.application.autores.v1.AutoresCallback;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "autoresCallback", targetNamespace = "http://fernando.com.br/server/application/autores/v1", wsdlLocation = "file:/home/fernando/workspace/soa-aplicado-cap-07-2/src/test/resources/webapp/WEB-INF/wsdl/autoresCallback.wsdl")
public class AutoresCallback_Service
    extends Service
{

    private final static URL AUTORESCALLBACK_WSDL_LOCATION;
    private final static WebServiceException AUTORESCALLBACK_EXCEPTION;
    private final static QName AUTORESCALLBACK_QNAME = new QName("http://fernando.com.br/server/application/autores/v1", "autoresCallback");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("file:/home/fernando/workspace/soa-aplicado-cap-07-2/src/test/resources/webapp/WEB-INF/wsdl/autoresCallback.wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        AUTORESCALLBACK_WSDL_LOCATION = url;
        AUTORESCALLBACK_EXCEPTION = e;
    }

    public AutoresCallback_Service() {
        super(__getWsdlLocation(), AUTORESCALLBACK_QNAME);
    }

    public AutoresCallback_Service(WebServiceFeature... features) {
        super(__getWsdlLocation(), AUTORESCALLBACK_QNAME, features);
    }

    public AutoresCallback_Service(URL wsdlLocation) {
        super(wsdlLocation, AUTORESCALLBACK_QNAME);
    }

    public AutoresCallback_Service(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, AUTORESCALLBACK_QNAME, features);
    }

    public AutoresCallback_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public AutoresCallback_Service(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns AutoresCallback
     */
    @WebEndpoint(name = "autoresCallbackSOAP")
    public AutoresCallback getAutoresCallbackSOAP() {
        return super.getPort(new QName("http://fernando.com.br/server/application/autores/v1", "autoresCallbackSOAP"), AutoresCallback.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns AutoresCallback
     */
    @WebEndpoint(name = "autoresCallbackSOAP")
    public AutoresCallback getAutoresCallbackSOAP(WebServiceFeature... features) {
        return super.getPort(new QName("http://fernando.com.br/server/application/autores/v1", "autoresCallbackSOAP"), AutoresCallback.class, features);
    }

    private static URL __getWsdlLocation() {
        if (AUTORESCALLBACK_EXCEPTION!= null) {
            throw AUTORESCALLBACK_EXCEPTION;
        }
        return AUTORESCALLBACK_WSDL_LOCATION;
    }

}
