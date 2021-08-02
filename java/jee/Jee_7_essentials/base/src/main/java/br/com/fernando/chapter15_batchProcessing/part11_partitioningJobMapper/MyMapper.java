package br.com.fernando.chapter15_batchProcessing.part11_partitioningJobMapper;

import java.util.Properties;

import javax.batch.api.partition.PartitionMapper;
import javax.batch.api.partition.PartitionPlan;
import javax.batch.api.partition.PartitionPlanImpl;
import javax.inject.Named;

@Named
public class MyMapper implements PartitionMapper {

    // The mapPartitions method returns an implementation of the PartitionPlan interface. 
    // This code returns PartitionPlanImpl , a convenient basic implementation of the PartitionPlan interface.
    @Override
    public PartitionPlan mapPartitions() throws Exception {
        return new PartitionPlanImpl() {

            // The getPartitions method returns the number of partitions.
            @Override
            public int getPartitions() {
                return 2;
            }

            // The getThreads method returns the number of threads used to concurrently execute the partitions. 
            // By default, the number of threads is equal to the number of partitions.
            @Override
            public int getThreads() {
                return 2;
            }

            //  The getPartitionProperties method returns an array of Properties for each partition.
            @Override
            public Properties[] getPartitionProperties() {
                final Properties[] props = new Properties[getPartitions()];

                for (int i = 0; i < getPartitions(); i++) {
                    props[i] = new Properties();
                    props[i].setProperty("start", String.valueOf(i * 10 + 1));
                    props[i].setProperty("end", String.valueOf((i + 1) * 10));
                }
                return props;
            }
        };
    }
}
