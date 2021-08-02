package br.com.fernando.chapter15_batchProcessing.part03_exceptionHandling;

import java.util.List;

import javax.batch.api.chunk.AbstractItemWriter;
import javax.inject.Named;

@Named
public class MyItemWriter extends AbstractItemWriter {
    private static int retries = 0;

    @Override
    public void writeItems(@SuppressWarnings("rawtypes") final List list) {
        if (retries <= 3 && list.contains(new MyOutputRecord(8))) {
            retries++;
            System.out.println("Throw UnsupportedOperationException in MyItemWriter");
            throw new UnsupportedOperationException();
        }

        System.out.println("MyItemWriter.writeItems: " + list);
    }
}
