package br.com.fernando.chapter15_batchProcessing.part12_scheduling;

import java.util.concurrent.TimeUnit;

import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.JobExecution;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class ExecuteBatchEJB {

    @Inject
    private MyManagedScheduledBatch managedScheduledBatch;

    public void doIt() throws Exception {

        final JobOperator jobOperator = BatchRuntime.getJobOperator();

        managedScheduledBatch.runJob();

        MyStepListener.countDownLatch.await(90, TimeUnit.SECONDS);

        // If this assert fails it means we've timed out above
        System.out.println(MyStepListener.countDownLatch.getCount());
        System.out.println(MyJob.executedBatchs.size());

        Thread.sleep(1000l);

        final JobExecution lastExecution = jobOperator.getJobExecution(MyJob.executedBatchs.get(2));

        System.out.println(lastExecution.getBatchStatus());

        for (Long executedBatch : MyJob.executedBatchs) {
            System.out.println("ManagedScheduledBatchTest checking completed for batch " + executedBatch);
        }
    }
}
