package br.com.fernando.client;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import br.com.fernando.core.Livro;

/**
 * This class was generated by the JAX-WS RI. JAX-WS RI 2.2.4-b01 Generated source version: 2.2
 * 
 */
@WebService(name = "ListagemLivros", targetNamespace = "http://application.server.fernando.com.br/")
@XmlSeeAlso({ ObjectFactory.class })
public interface ListagemLivros {

    /**
     * 
     * @return returns java.util.List<br.com.fernando.client.Livro>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "listarLivros", targetNamespace = "http://application.server.fernando.com.br/", className = "br.com.fernando.client.ListarLivros")
    @ResponseWrapper(localName = "listarLivrosResponse", targetNamespace = "http://application.server.fernando.com.br/", className = "br.com.fernando.client.ListarLivrosResponse")
    @Action(input = "http://application.server.fernando.com.br/ListagemLivros/listarLivrosRequest", output = "http://application.server.fernando.com.br/ListagemLivros/listarLivrosResponse")
    public List<Livro> listarLivros();

}