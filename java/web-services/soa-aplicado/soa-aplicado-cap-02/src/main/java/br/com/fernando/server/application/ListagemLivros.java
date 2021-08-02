package br.com.fernando.server.application;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import br.com.fernando.core.Livro;
import br.com.fernando.server.persistence.LivroDAO;

@WebService(serviceName = "livros")
public class ListagemLivros {

    // A anotacao '@WebResult' define a unidade da xml, Veja o arquivo 'responseListarLivros.xml'
    @WebResult(name = "livro")
    //
    // A anotacao '@WebMethod' defini o nome do metodo, se omitido sera o nome do metodo java
    @WebMethod(operationName = "listarLivros")
    //
    // Cada metodo tem um elemento do XSD Schema gerado, entao devemos mapear a mensagem, veja o arquivo 'wsdl.xml'
    // Aqui tanto a requisicao e resposta precisam ser mapeados
    @RequestWrapper(localName = "listarLivros", className = "br.com.fernando.server.application.jaxws.ListarLivros")
    @ResponseWrapper(localName = "listarLivrosResponse", className = "br.com.fernando.server.application.jaxws.ListarLivrosResponse")
    public List<Livro> listarLivros() {
        final LivroDAO livroDAO = new LivroDAO();

        return livroDAO.listarLivros();
    }

    @WebResult(name = "livro")
    @WebMethod(operationName = "listarLivrosPaginacao")
    @RequestWrapper(localName = "listarLivrosPaginacao", className = "br.com.fernando.server.application.jaxws.ListarLivrosPaginacao")
    @ResponseWrapper(localName = "listarLivrosPaginacaoResponse", className = "br.com.fernando.server.application.jaxws.ListarLivrosPaginacaoResponse")
    //
    // Com a anotacao '@WebParam' podemos definir o nome dos parametros para que o xml de requisicao tenha um nome mais legivel
    public List<Livro> listarLivros(@WebParam(name = "numeroDaPagina") final int numeroDaPagina, @WebParam(name = "tamanhoDaPagina") final int tamanhoDaPagina) {

        final LivroDAO livroDAO = new LivroDAO();

        return livroDAO.listarLivros(numeroDaPagina, tamanhoDaPagina);
    }
}
