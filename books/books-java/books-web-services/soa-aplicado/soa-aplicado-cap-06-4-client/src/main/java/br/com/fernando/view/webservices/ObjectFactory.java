
package br.com.fernando.view.webservices;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the br.com.fernando.view.webservices package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ListarLivros_QNAME = new QName("http://webservices.view.fernando.com.br/", "listarLivros");
    private final static QName _ListarLivrosResponse_QNAME = new QName("http://webservices.view.fernando.com.br/", "listarLivrosResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: br.com.fernando.view.webservices
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ListarLivrosResponse }
     * 
     */
    public ListarLivrosResponse createListarLivrosResponse() {
        return new ListarLivrosResponse();
    }

    /**
     * Create an instance of {@link ListarLivros }
     * 
     */
    public ListarLivros createListarLivros() {
        return new ListarLivros();
    }

    /**
     * Create an instance of {@link Livro }
     * 
     */
    public Livro createLivro() {
        return new Livro();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListarLivros }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservices.view.fernando.com.br/", name = "listarLivros")
    public JAXBElement<ListarLivros> createListarLivros(ListarLivros value) {
        return new JAXBElement<ListarLivros>(_ListarLivros_QNAME, ListarLivros.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListarLivrosResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservices.view.fernando.com.br/", name = "listarLivrosResponse")
    public JAXBElement<ListarLivrosResponse> createListarLivrosResponse(ListarLivrosResponse value) {
        return new JAXBElement<ListarLivrosResponse>(_ListarLivrosResponse_QNAME, ListarLivrosResponse.class, null, value);
    }

}
