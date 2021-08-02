package br.com.fernando.chapter15_batchProcessing.part08_split;

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

        // <1> Make sure all the steps were executed.
        System.out.println(stepExecutions.size()); // 3

        System.out.println(executedSteps.contains("step1"));
        System.out.println(executedSteps.contains("step2"));
        System.out.println(executedSteps.contains("step3"));

        // <2> Steps 'step1' and 'step2' can appear in any order, since they were executed in parallel.
        if (executedSteps.size() > 1) {
            System.out.println(executedSteps.get(0).equals("step1") || executedSteps.get(0).equals("step2"));
        }

        if (executedSteps.size() > 2) {
            System.out.println(executedSteps.get(1).equals("step1") || executedSteps.get(1).equals("step2"));
        }

        // <3> Step 'step3' is always the last to be executed.
        if (executedSteps.size() > 3) {
            System.out.println(executedSteps.get(2).equals("step3"));
        }

        // <4> Job should be completed.
        System.out.println(jobExecution.getBatchStatus());

    }
}
