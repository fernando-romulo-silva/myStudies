package br.com.fernando.chapter15_batchProcessing.part10_partitioningJob;

import javax.batch.api.chunk.ItemProcessor;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class MyItemProcessor implements ItemProcessor {
    public static int totalProcessors = 0;
    private final int processorId;

    @Inject
    JobContext context;

    public MyItemProcessor() {
        processorId = ++totalProcessors;
    }

    @Override
    public MyOutputRecord processItem(final Object t) {
        System.out.format("processItem (%d): %s\n", processorId, t);

        return (((MyInputRecord) t).getId() % 2 == 0) ? null : new MyOutputRecord(((MyInputRecord) t).getId() * 2);
    }
}
