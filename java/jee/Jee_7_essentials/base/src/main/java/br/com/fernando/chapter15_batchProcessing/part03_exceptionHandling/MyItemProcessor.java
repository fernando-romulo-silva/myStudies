package br.com.fernando.chapter15_batchProcessing.part03_exceptionHandling;

import javax.batch.api.chunk.ItemProcessor;
import javax.inject.Named;

@Named
public class MyItemProcessor implements ItemProcessor {

    @Override
    public Object processItem(final Object t) {
        System.out.println("MyItemProcessor.processItem: " + t);

        if (((MyInputRecord) t).getId() == 6) {
            throw new NullPointerException();
        }

        return new MyOutputRecord(((MyInputRecord) t).getId());
    }
}
