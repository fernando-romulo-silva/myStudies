package br.com.fernando.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

import br.com.fernando.core.Livro;

/**
 * This object contains factory methods for each Java content interface and Java element interface generated in the br.com.fernando.client package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the Java representation for XML content. The Java representation of XML content can consist of schema derived interfaces and classes representing the binding of schema type
 * definitions, element declarations and model groups. Factory methods for each of these are provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ListarLivrosResponse_QNAME = new QName("http://application.server.fernando.com.br/", "listarLivrosResponse");
    private final static QName _ListarLivros_QNAME = new QName("http://application.server.fernando.com.br/", "listarLivros");
    private final static QName _ListarLivrosPaginacao_QNAME = new QName("http://application.server.fernando.com.br/", "listarLivrosPaginacao");
    private final static QName _ListarLivrosPaginacaoResponse_QNAME = new QName("http://application.server.fernando.com.br/", "listarLivrosPaginacaoResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: br.com.fernando.client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Livro }
     * 
     */
    public Livro createLivro() {
        return new Livro();
    }

    /**
     * Create an instance of {@link ListarLivrosPaginacao }
     * 
     */
    public ListarLivrosPaginacao createListarLivrosPaginacao() {
        return new ListarLivrosPaginacao();
    }

    /**
     * Create an instance of {@link ListarLivros }
     * 
     */
    public ListarLivros createListarLivros() {
        return new ListarLivros();
    }

    /**
     * Create an instance of {@link ListarLivrosResponse }
     * 
     */
    public ListarLivrosResponse createListarLivrosResponse() {
        return new ListarLivrosResponse();
    }

    /**
     * Create an instance of {@link ListarLivrosPaginacaoResponse }
     * 
     */
    public ListarLivrosPaginacaoResponse createListarLivrosPaginacaoResponse() {
        return new ListarLivrosPaginacaoResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListarLivrosResponse }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://application.server.fernando.com.br/", name = "listarLivrosResponse")
    public JAXBElement<ListarLivrosResponse> createListarLivrosResponse(final ListarLivrosResponse value) {
        return new JAXBElement<ListarLivrosResponse>(_ListarLivrosResponse_QNAME, ListarLivrosResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListarLivros }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://application.server.fernando.com.br/", name = "listarLivros")
    public JAXBElement<ListarLivros> createListarLivros(final ListarLivros value) {
        return new JAXBElement<ListarLivros>(_ListarLivros_QNAME, ListarLivros.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListarLivrosPaginacao }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://application.server.fernando.com.br/", name = "listarLivrosPaginacao")
    public JAXBElement<ListarLivrosPaginacao> createListarLivrosPaginacao(final ListarLivrosPaginacao value) {
        return new JAXBElement<ListarLivrosPaginacao>(_ListarLivrosPaginacao_QNAME, ListarLivrosPaginacao.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListarLivrosPaginacaoResponse }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://application.server.fernando.com.br/", name = "listarLivrosPaginacaoResponse")
    public JAXBElement<ListarLivrosPaginacaoResponse> createListarLivrosPaginacaoResponse(final ListarLivrosPaginacaoResponse value) {
        return new JAXBElement<ListarLivrosPaginacaoResponse>(_ListarLivrosPaginacaoResponse_QNAME, ListarLivrosPaginacaoResponse.class, null, value);
    }

}
