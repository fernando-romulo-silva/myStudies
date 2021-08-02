package br.com.fernando.chapter15_batchProcessing.part04_batchletProcessing;

import java.util.Properties;

import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.JobExecution;
import javax.ejb.Stateless;

import br.com.fernando.utils.BatchTestHelper;

@Stateless
public class ExecuteBatchEJB {

    public void doIt() throws Exception {
        final JobOperator jobOperator = BatchRuntime.getJobOperator();

        final Long executionId = jobOperator.start("myJob", new Properties());
        JobExecution jobExecution = jobOperator.getJobExecution(executionId);

        jobExecution = BatchTestHelper.keepTestAlive(jobExecution);

        // <1> Job should be completed.
        System.out.println(jobExecution.getBatchStatus());
    }
}
