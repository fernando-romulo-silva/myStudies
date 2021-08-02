package br.com.fernando.client;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;

@WebServiceClient(name = "ListagemLivrosService", targetNamespace = "http://application.server.fernando.com.br/", wsdlLocation = "http://localhost:8080/soa-aplicado/livros?wsdl")
public class ListagemLivrosService extends Service {

    private final static URL LISTAGEMLIVROSSERVICE_WSDL_LOCATION;
    private final static WebServiceException LISTAGEMLIVROSSERVICE_EXCEPTION;
    private final static QName LISTAGEMLIVROSSERVICE_QNAME = new QName("http://application.server.fernando.com.br/", "livros");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://localhost:8080/soa-aplicado/livros?wsdl");
        } catch (final MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        LISTAGEMLIVROSSERVICE_WSDL_LOCATION = url;
        LISTAGEMLIVROSSERVICE_EXCEPTION = e;
    }

    public ListagemLivrosService() {
        super(__getWsdlLocation(), LISTAGEMLIVROSSERVICE_QNAME);
    }

    public ListagemLivrosService(final WebServiceFeature... features) {
        super(__getWsdlLocation(), LISTAGEMLIVROSSERVICE_QNAME, features);
    }

    public ListagemLivrosService(final URL wsdlLocation) {
        super(wsdlLocation, LISTAGEMLIVROSSERVICE_QNAME);
    }

    public ListagemLivrosService(final URL wsdlLocation, final WebServiceFeature... features) {
        super(wsdlLocation, LISTAGEMLIVROSSERVICE_QNAME, features);
    }

    public ListagemLivrosService(final URL wsdlLocation, final QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ListagemLivrosService(final URL wsdlLocation, final QName serviceName, final WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return returns ListagemLivros
     */
    @WebEndpoint(name = "ListagemLivrosPort")
    public ListagemLivros getListagemLivrosPort() {
        return super.getPort(new QName("http://application.server.fernando.com.br/", "ListagemLivrosPort"), ListagemLivros.class);
    }

    /**
     * 
     * @param features
     *            A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy. Supported features not in the <code>features</code> parameter will have their default values.
     * @return returns ListagemLivros
     */
    @WebEndpoint(name = "ListagemLivrosPort")
    public ListagemLivros getListagemLivrosPort(final WebServiceFeature... features) {
        return super.getPort(new QName("http://application.server.fernando.com.br/", "ListagemLivrosPort"), ListagemLivros.class, features);
    }

    private static URL __getWsdlLocation() {
        if (LISTAGEMLIVROSSERVICE_EXCEPTION != null) {
            throw LISTAGEMLIVROSSERVICE_EXCEPTION;
        }
        return LISTAGEMLIVROSSERVICE_WSDL_LOCATION;
    }

}
