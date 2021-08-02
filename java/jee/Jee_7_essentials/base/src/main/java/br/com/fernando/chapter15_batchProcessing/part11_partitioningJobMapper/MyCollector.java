package br.com.fernando.chapter15_batchProcessing.part11_partitioningJobMapper;

import java.io.Serializable;

import javax.batch.api.partition.PartitionCollector;
import javax.inject.Named;

@Named
public class MyCollector implements PartitionCollector {

    @Override
    public Serializable collectPartitionData() throws Exception {
        System.out.println("collectPartitionData");

        return new Serializable() {

            private static final long serialVersionUID = 1L;

        };
    }
}
