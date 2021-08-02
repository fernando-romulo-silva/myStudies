package br.com.fernando.chapter15_batchProcessing.part02_customCheckpointing;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.BatchStatus;
import javax.batch.runtime.JobExecution;
import javax.batch.runtime.Metric;
import javax.batch.runtime.StepExecution;
import javax.ejb.Stateless;

import br.com.fernando.utils.BatchTestHelper;

@Stateless
public class ExecuteBatchEJB {

    public void doIt() throws Exception {
        final JobOperator jobOperator = BatchRuntime.getJobOperator();

        final Properties properties = new Properties();

        final Long executionId = jobOperator.start("myJob", properties); // Convention name myJob.xml

        final JobExecution jobExecution = jobOperator.getJobExecution(executionId);

        final List<StepExecution> stepExecutions = jobOperator.getStepExecutions(executionId);

        Map<Metric.MetricType, Long> metricsMap;

        for (final StepExecution stepExecution : stepExecutions) {

            if (stepExecution.getStepName().equals("myStep")) {

                metricsMap = BatchTestHelper.getMetricsMap(stepExecution.getMetrics());

                // <1> The read count should be 10 elements. Check +MyItemReader+.
                System.out.println(metricsMap.get(Metric.MetricType.READ_COUNT).longValue());

                // <2> The write count should be 5. Only half of the elements read are processed to be written.
                System.out.println(metricsMap.get(Metric.MetricType.WRITE_COUNT).longValue());

                // <3> The commit count should be 4. Checkpoint is on every 3rd read, 4 commits for read elements.
                System.out.println(metricsMap.get(Metric.MetricType.COMMIT_COUNT).longValue());
            }
        }

        System.out.println(jobExecution.getBatchStatus() == BatchStatus.COMPLETED);
    }
}
