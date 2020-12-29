package br.com.fernando.server.application;

import java.util.List;

import javax.ejb.Stateless;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.xml.soap.SOAPException;

import br.com.fernando.core.Livro;

@WebService
@Stateless
public class LivrosService {

    @PersistenceContext
    private EntityManager em;

    @WebResult(name = "livro")
    public List<Livro> listarLivros() {
        return em.createQuery("select distinct l from Livro l left join FETCH l.autores", Livro.class).getResultList();
    }

    @WebResult(name = "livro")
    public List<Livro> listarLivrosPaginacao(final int numeroDaPagina, final int tamanhoDaPagina) {

        final TypedQuery<Livro> query = em.createQuery("select distinct l from Livro l left join FETCH l.autores", Livro.class);
        query.setFirstResult(numeroDaPagina * tamanhoDaPagina);
        query.setMaxResults(tamanhoDaPagina);
        return query.getResultList();
    }

    public void criarLivro(@WebParam(name = "livro") final Livro livro) throws UsuarioNaoAutorizadoException, SOAPException {
        em.persist(livro);
    }
}
