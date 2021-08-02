package br.com.fernando.chapter15_batchProcessing.part05_listeners;

import static java.util.concurrent.TimeUnit.SECONDS;

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

                System.out.println("READ_COUNT: " + metricsMap.get(Metric.MetricType.READ_COUNT).longValue());
                System.out.println("WRITE_COUNT: " + metricsMap.get(Metric.MetricType.WRITE_COUNT).longValue());
                System.out.println("COMMIT_COUNT: " + metricsMap.get(Metric.MetricType.COMMIT_COUNT).longValue());
            }
        }

        UtilRecorder.countDownLatch.await(0, SECONDS);

        System.out.println(jobExecution.getBatchStatus());
    }
}
