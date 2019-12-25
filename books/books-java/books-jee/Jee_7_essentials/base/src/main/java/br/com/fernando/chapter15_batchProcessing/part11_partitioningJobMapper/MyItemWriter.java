package br.com.fernando.chapter15_batchProcessing.part11_partitioningJobMapper;

import java.util.List;

import javax.batch.api.chunk.AbstractItemWriter;
import javax.inject.Named;

@Named
public class MyItemWriter extends AbstractItemWriter {

    @Override
    public void writeItems(@SuppressWarnings("rawtypes") final List list) {
        System.out.println("writeItems: " + list);
    }
}
