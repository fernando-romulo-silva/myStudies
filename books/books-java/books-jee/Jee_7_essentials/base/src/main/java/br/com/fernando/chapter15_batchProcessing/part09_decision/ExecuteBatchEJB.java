package br.com.fernando.chapter15_batchProcessing.part09_decision;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.JobExecution;
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
        final List<String> executedSteps = new ArrayList<>();

        for (final StepExecution stepExecution : stepExecutions) {
            executedSteps.add(stepExecution.getStepName());
        }

        // <1> Make sure that only two steps were executed.
        System.out.println(stepExecutions.size());

        // <2> Make sure that only the expected steps were executed an in order.
        System.out.println(executedSteps.toArray()); // new String[]{ "step1", "step3" }

        // <3> Make sure that this step was never executed.
        System.out.println(executedSteps.contains("step2")); // false

        // <4> Job should be completed.
        System.out.println(jobExecution.getBatchStatus()); // COMPLETED
    }
}
