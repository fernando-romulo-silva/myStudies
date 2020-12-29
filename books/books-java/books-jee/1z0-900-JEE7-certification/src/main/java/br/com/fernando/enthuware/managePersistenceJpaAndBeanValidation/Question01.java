package br.com.fernando.enthuware.managePersistenceJpaAndBeanValidation;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class Question01 {

    // Given the JPQL code fragment:
    //
    // select pub.title, pub.author, pub.pages FROM Publisher pub
    //
    // Which clauses can you add to this JPQL query to retrieve only those books with between 500 and 750 total pages?
    //
    // You had to select 2 options
    //
    // A
    // WHERE MIN(pages) >= 500 AND MAX(pages) <= 750
    //
    // B
    // WHERE pub.pages <= 500 OR pub.pages >= 750
    //
    // C
    // WHERE pub.pages BETWEEN 500 AND 750
    //
    // D
    // WHERE pub.pages => 500 AND pub.pages <= 750
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //    
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    // The C and D are corrects
    //

    @PersistenceContext
    public EntityManager em;

    @SuppressWarnings("unchecked")
    public List<Publisher> findWithName(String name) {

	final String qlString = new StringBuilder("select pub.title, pub.author, pub.pages FROM Publisher pub") //
		.append("WHERE pub.pages BETWEEN 500 AND 750") //
		// or
		.append("WHERE pub.pages >= 500 AND pub.pages <= 750") //
		.toString();

	return em.createQuery(qlString) //
		.getResultList();

    }

    class Publisher {

    }

}
