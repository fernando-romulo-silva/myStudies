package br.com.fernando.chapter15_batchProcessing.part13_csvDatabase;

import java.util.List;
import javax.batch.api.chunk.AbstractItemWriter;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Named
public class MyItemWriter extends AbstractItemWriter {

    @PersistenceContext
    EntityManager em;

    @Override
    public void writeItems(@SuppressWarnings("rawtypes") List list) {
        System.out.println("writeItems: " + list);
        for (Object person : list) {
            em.persist(person);
        }
    }
}
