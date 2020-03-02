package br.com.fernando.server.application;

import java.util.List;

import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.fernando.core.Autor;

@WebService(serviceName = "autores")
@Stateless
public class AutoresService {

    @PersistenceContext(name = "soa-aplicadoPU")
    private EntityManager em;

    @WebResult(name = "autor")
    @WebMethod(operationName = "listarAutores")
    public List<Autor> listarAutores() {
        return em.createQuery("select a from Autor a", Autor.class).getResultList();
    }
}
