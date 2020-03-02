package br.com.fernando.server.application;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.sql.DataSource;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPFault;
import javax.xml.ws.soap.SOAPFaultException;

import br.com.fernando.core.Livro;
import br.com.fernando.core.Usuario;

// Neste caso, um dos princípios de orientação a serviços que vêm à tona é o da falta
// de manutenção de estado. Este princípio diz que um serviço não deve, nunca, manter
// informações a respeito do estado. Em outras palavras, uma requisição N nunca pode
// ser dependente de uma requisição anterior. O que faz dos beans sem informação de
// estado a escolha adequada para web services.
//
// Você decide fazer o teste transformando uma das classes de web services do seu projeto em EJB 
// (que, na prática, consiste apenas de anotar a classe com @Stateles)

@WebService(serviceName = "livros")
@Stateless
public class LivrosService {

    @Resource(name = "jdbc/soa-aplicadoDS")
    private DataSource dataSource;

    @PersistenceContext(name = "soa-aplicadoPU")
    private EntityManager em;

    @WebMethod(operationName = "listarLivros")
    @WebResult(name = "livro")
    public List<Livro> listarLivros() {
        return em.createQuery("select distinct l from Livro l left join FETCH l.autores", Livro.class).getResultList();
    }

    @WebMethod(operationName = "listarLivrosPaginacao")
    @WebResult(name = "livro")
    public List<Livro> listarLivrosPaginacao(final int numeroDaPagina, final int tamanhoDaPagina) {

        final TypedQuery<Livro> query = em.createQuery("select distinct l from Livro l left join FETCH l.autores", Livro.class);
        query.setFirstResult(numeroDaPagina * tamanhoDaPagina);
        query.setMaxResults(tamanhoDaPagina);
        return query.getResultList();
    }

    public void criarLivro(@WebParam(name = "livro") final Livro livro, @WebParam(name = "usuario", header = true) final Usuario usuario) throws UsuarioNaoAutorizadoException, SOAPException {
        if (usuario.getLogin().equals("soa") && usuario.getSenha().equals("soa")) {
            em.persist(livro);
        } else if (usuario.getNome().equals("faultCode")) {
            final SOAPFault soapFault = SOAPFactory.newInstance().createFault("Usuário não autorizado", new QName(SOAPConstants.URI_NS_SOAP_1_1_ENVELOPE, "Client.autorizacao"));
            soapFault.setFaultActor("http://application.server.fernando.com.br/LivrosService");
            throw new SOAPFaultException(soapFault);
        } else {
            throw new UsuarioNaoAutorizadoException("Usuário não autorizado");
        }
    }
}
