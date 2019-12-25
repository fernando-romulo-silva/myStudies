package br.com.fernando.chapter15_batchProcessing.part03_exceptionHandling;

import static java.util.concurrent.TimeUnit.SECONDS;
import static javax.batch.runtime.Metric.MetricType.PROCESS_SKIP_COUNT;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.JobExecution;
import javax.batch.runtime.Metric.MetricType;
import javax.batch.runtime.StepExecution;
import javax.ejb.Stateless;

import br.com.fernando.utils.BatchTestHelper;

@Stateless
public class ExecuteBatchEJB {

    public void doIt() throws Exception {
        final JobOperator jobOperator = BatchRuntime.getJobOperator();

        final Long executionId = jobOperator.start("myJob", new Properties());
        JobExecution jobExecution = jobOperator.getJobExecution(executionId);

        jobExecution = BatchTestHelper.keepTestAlive(jobExecution);

        final List<StepExecution> stepExecutions = jobOperator.getStepExecutions(executionId);
        for (final StepExecution stepExecution : stepExecutions) {
            if (stepExecution.getStepName().equals("myStep")) {
                final Map<MetricType, Long> metricsMap = BatchTestHelper.getMetricsMap(stepExecution.getMetrics());

                // TODO: Both WildFLy and Payara have a 2 here, but the test originally tested
                // for 1. Needs investigation.

                final long skipCount = metricsMap.get(PROCESS_SKIP_COUNT).longValue();

                System.out.println("skipCount: " + skipCount);

                // There are a few differences between Glassfish and Wildfly. Needs investigation.
                //assertEquals(1L, metricsMap.get(Metric.MetricType.WRITE_SKIP_COUNT).longValue());
                //assertEquals(1L, retryReadExecutions);
                System.out.println("retryReadExecutions=" + UtilRecorder.retryReadExecutions);
            }
        }

        UtilRecorder.chunkExceptionsCountDownLatch.await(0, SECONDS);
        jobExecution.getBatchStatus();
    }
}
