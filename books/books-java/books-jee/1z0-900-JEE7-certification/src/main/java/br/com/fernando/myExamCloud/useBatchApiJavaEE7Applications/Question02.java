package br.com.fernando.myExamCloud.useBatchApiJavaEE7Applications;

import java.util.Properties;

import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.JobExecution;

public class Question02 {

    // When a batch job is submitted, the batch runtime creates an instance of JobExecution to track it.
    // Which of the following code obtain JobExecution for the given executionId?
    //
    // Choice A
    // JobExecution.getJobExecution(executionId)
    //
    // Choice B
    // JobOperator.getJobExecution(executionId)
    //
    // Choice C
    // JobExecution.getInstance(executionId)
    //
    // Choice D
    // JobOperatorFactory.getJobExecution(executionId)
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    // Choice B is correct.
    //
    // When a batch job is submitted, the batch runtime creates an instance of JobExecution to track it.
    //
    // JobExecution has methods to obtain various details such as the job start time, job completion time, job exit status, and so on.
    //
    // To obtain the JobExecution for an execution ID, you can use the JobOperator.getJobExecution(executionId) method.

    public void executeBatch() {
	final JobOperator jobOperator = BatchRuntime.getJobOperator();

	// You can restart the job using the JobOperator.restart method:
	final Properties properties = new Properties();

	final Long executionId = jobOperator.start("myJob", properties); // Convention name myJob.xml

	final JobExecution jobExecution = jobOperator.getJobExecution(executionId);
    }

    // Listing shows the definition of JobExecution:

    public interface JobExampleExecution {
	long getExecutionId();

	java.lang.String getJobName();

	javax.batch.runtime.BatchStatus getBatchStatus();

	java.util.Date getStartTime();

	java.util.Date getEndTime();

	java.lang.String getExitStatus();

	java.util.Date getCreateTime();

	java.util.Date getLastUpdatedTime();

	java.util.Properties getJobParameters();
    }
}
