package br.com.fernando.server.application;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;

import br.com.fernando.core.Livro;
import br.com.fernando.server.persistence.LivroDAO;

// Para criar um web service WSDL use a anotacao '@WebService' 
@WebService(serviceName = "livros")
public class ListagemLivros {

    // Web services podem ter varios metodos
    // Com a anotacao '@WebMethod' definimos que este metodo retorna uma lista de objetos 'Livro' (na verdade sera
    // convertido em xml soap)
    @WebMethod
    public List<Livro> listarLivros() {
        final LivroDAO livroDAO = new LivroDAO();

        return livroDAO.listarLivros();
    }

    // O pacote de classes do cliente sao geradas atraves do comando
    // wsimport -p br.com.fernando.client -Xnocompile http://localhost:8080/soa-aplicado/livros?wsdl
    //
    // Cria um servidor do web service sem precisar de um servidor, porem a 'Endpoint' não é capaz
    // de publicar mais de um serviço na mesma porta
    public static void main(final String[] args) {

        Endpoint.publish("http://localhost:8080/soa-aplicado/livros", new ListagemLivros());

        System.out.println("Serviço inicializado!");
    }
}
