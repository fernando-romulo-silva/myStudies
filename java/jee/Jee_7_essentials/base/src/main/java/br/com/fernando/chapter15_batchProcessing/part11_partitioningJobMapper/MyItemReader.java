package br.com.fernando.chapter15_batchProcessing.part11_partitioningJobMapper;

import java.io.Serializable;
import java.util.StringTokenizer;

import javax.batch.api.BatchProperty;
import javax.batch.api.chunk.AbstractItemReader;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class MyItemReader extends AbstractItemReader {
    public static int totalReaders = 0;
    private int readerId;

    private StringTokenizer tokens;

    @Inject
    @BatchProperty(name = "start")
    private String startProp;

    @Inject
    @BatchProperty(name = "end")
    private String endProp;

    @Inject
    private JobContext context;

    @Override
    public void open(final Serializable e) {
        final int start = new Integer(startProp);
        final int end = new Integer(endProp);
        final StringBuilder builder = new StringBuilder();
        for (int i = start; i <= end; i++) {
            builder.append(i);
            if (i < end)
                builder.append(",");
        }

        readerId = ++totalReaders;
        tokens = new StringTokenizer(builder.toString(), ",");
    }

    @Override
    public MyInputRecord readItem() {

        System.out.println(context.getExecutionId());

        if (tokens.hasMoreTokens()) {
            final int token = Integer.valueOf(tokens.nextToken());
            System.out.format("readItem (%d): %d\n", readerId, token);
            return new MyInputRecord(token);
        }
        return null;
    }
}