package br.com.fernando.chapter15_batchProcessing.part02_customCheckpointing;

import java.util.concurrent.CountDownLatch;

import javax.batch.api.chunk.AbstractCheckpointAlgorithm;
import javax.batch.api.chunk.CheckpointAlgorithm;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class MyCheckpointAlgorithm extends AbstractCheckpointAlgorithm implements CheckpointAlgorithm {

    public static CountDownLatch checkpointCountDownLatch = new CountDownLatch(10);

    @Inject
    private JobContext jobContext;

    // In this code, isReadyToCheckpoint is invoked by the runtime as each item is read to determine if it is time to checkpoint the current chunk.
    // The method returns true if the chunk needs to be checkpointed, and false otherwise.
    @Override
    public boolean isReadyToCheckpoint() throws Exception {

        System.out.println("Execution Id: " + jobContext.getExecutionId());

        if (MyItemReader.COUNT % 5 == 0) {
            return true;
        } else {
            return false;
        }
    }

}
