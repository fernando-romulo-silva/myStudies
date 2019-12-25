package br.com.fernando.chapter15_batchProcessing.part10_partitioningJob;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.JobExecution;
import javax.batch.runtime.Metric;
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
                final Map<Metric.MetricType, Long> metricsMap = BatchTestHelper.getMetricsMap(stepExecution.getMetrics());

                // <1> The read count should be 20 elements. Check +MyItemReader+.
                System.out.println(metricsMap.get(Metric.MetricType.READ_COUNT).longValue()); // 20L

                // <2> The write count should be 10. Only half of the elements read are processed to be written.
                System.out.println(metricsMap.get(Metric.MetricType.WRITE_COUNT).longValue()); // 10L

                // Number of elements by the item count value on myJob.xml, plus an additional transaction for the remaining elements by each partition.
                // final long commitCount = (10L / 3 + (10 % 3 > 0 ? 1 : 0)) * 2;

                // <3> The commit count should be 8. Checkpoint is on every 3rd read, 4 commits for read elements and 2 partitions.
                System.out.println(metricsMap.get(Metric.MetricType.COMMIT_COUNT).longValue());
            }
        }

        // <4> Job should be completed.
        System.out.println(jobExecution.getBatchStatus()); // COMPLETED
    }
}
