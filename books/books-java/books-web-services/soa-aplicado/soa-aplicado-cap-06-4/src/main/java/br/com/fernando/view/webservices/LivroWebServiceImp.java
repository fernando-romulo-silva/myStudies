package br.com.fernando.view.webservices;

import java.util.List;

import javax.jws.WebService;

import org.apache.cxf.interceptor.InInterceptors;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.fernando.application.LivroServiceApplication;
import br.com.fernando.domain.Livro;

@WebService(name = "livroWebService", endpointInterface = "br.com.fernando.view.webservices.LivroWebService")
@InInterceptors(interceptors = { "br.com.fernando.view.util.WSSecurityPhaseInterceptor" })
public class LivroWebServiceImp implements LivroWebService {

    @Autowired
    private LivroServiceApplication livroServiceApplication;

    @Override
    public List<Livro> listarLivros() {
        return livroServiceApplication.listarLivros();
    }
}
