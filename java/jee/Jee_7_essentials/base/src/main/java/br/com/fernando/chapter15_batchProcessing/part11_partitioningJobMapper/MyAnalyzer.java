package br.com.fernando.chapter15_batchProcessing.part11_partitioningJobMapper;

import java.io.Serializable;

import javax.batch.api.partition.PartitionAnalyzer;
import javax.batch.runtime.BatchStatus;
import javax.inject.Named;

@Named
public class MyAnalyzer implements PartitionAnalyzer {

    @Override
    public void analyzeCollectorData(final Serializable srlzbl) throws Exception {
        System.out.println("analyzeCollectorData");
    }

    @Override
    public void analyzeStatus(final BatchStatus bs, final String string) throws Exception {
        System.out.println("analyzeStatus");
    }

}
