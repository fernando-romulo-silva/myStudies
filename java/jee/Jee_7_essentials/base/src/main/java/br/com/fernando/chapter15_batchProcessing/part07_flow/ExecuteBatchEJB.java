package br.com.fernando.chapter15_batchProcessing.part07_flow;

import java.util.ArrayList;
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

        Long executionId = jobOperator.start("myJob", new Properties());
        JobExecution jobExecution = jobOperator.getJobExecution(executionId);

        jobExecution = BatchTestHelper.keepTestAlive(jobExecution);

        List<StepExecution> stepExecutions = jobOperator.getStepExecutions(executionId);
        List<String> executedSteps = new ArrayList<>();
        for (StepExecution stepExecution : stepExecutions) {
            executedSteps.add(stepExecution.getStepName());

            if (stepExecution.getStepName().equals("step2")) {
                Map<Metric.MetricType, Long> metricsMap = BatchTestHelper.getMetricsMap(stepExecution.getMetrics());
                System.out.println(metricsMap);
                System.out.println(metricsMap.get(Metric.MetricType.READ_COUNT).longValue());
                System.out.println(metricsMap.get(Metric.MetricType.WRITE_COUNT).longValue());
                System.out.println(metricsMap.get(Metric.MetricType.COMMIT_COUNT).longValue());
            }
        }

        // <1> Make sure all the steps were executed.
        System.out.println(stepExecutions.size());

        // <2> Make sure all the steps were executed in order of declaration.
        System.out.println(executedSteps.toArray()); // "step1", "step2", "step3"

        // <3> Job should be completed.
        System.out.println(jobExecution.getBatchStatus());
    }
}
