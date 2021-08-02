package br.com.fernando.chapter15_batchProcessing.part02_customCheckpointing;

import javax.batch.api.chunk.ItemProcessor;
import javax.inject.Named;

@Named
public class MyItemProcessor implements ItemProcessor {

    @Override
    public MyOutputRecord processItem(final Object t) {
        System.out.println("processItem: " + t);

        return (((MyInputRecord) t).getId() % 2 == 0) ? null : new MyOutputRecord(((MyInputRecord) t).getId() * 2);
    }
}
