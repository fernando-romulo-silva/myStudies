package br.com.fernando.chapter15_batchProcessing.part07_flow;

import java.util.List;
import javax.batch.api.chunk.AbstractItemWriter;
import javax.inject.Named;

@Named
public class MyItemWriter extends AbstractItemWriter {

    @Override
    public void writeItems(@SuppressWarnings("rawtypes") List list) {
        System.out.println("writeItems: " + list);
    }
}
