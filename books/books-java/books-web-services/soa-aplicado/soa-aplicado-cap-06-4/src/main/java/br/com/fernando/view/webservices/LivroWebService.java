package br.com.fernando.view.webservices;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;

import br.com.fernando.domain.Livro;

@WebService(name = "livroWebService")
public interface LivroWebService {

    @WebResult(name = "livro")
    @WebMethod(operationName = "listarLivros")
    public List<Livro> listarLivros();

}