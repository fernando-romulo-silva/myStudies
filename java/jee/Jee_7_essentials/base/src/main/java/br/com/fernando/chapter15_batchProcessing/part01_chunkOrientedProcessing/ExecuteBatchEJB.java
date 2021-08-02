package br.com.fernando.chapter15_batchProcessing.part01_chunkOrientedProcessing;

import java.util.Date;
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
        // If the Job XML is defined in a file named myJob.xml and packaged in the META-INF/batch-jobs directory in the classpath,
        // then we can start this chunk-oriented job using JobOperator:

        // JobOperator provides the interface for operating on batch jobs.
        // The start method creates a new job instance and starts the first execution of that instance.
        final JobOperator jobOperator = BatchRuntime.getJobOperator();

        // You can restart the job using the JobOperator.restart method:
        final Properties properties = new Properties();

        final Long executionId = jobOperator.start("myJob", properties); // Convention name myJob.xml

        final JobExecution jobExecution = jobOperator.getJobExecution(executionId);
        // jobExecution = BatchTestHelper.keepTestAlive(jobExecution);

        // The number of instances of a job with a particular name can be found as follows:
        final int count = jobOperator.getJobInstanceCount("myJob");
        // In this code, the number of myJob instances submitted by this application, running or not, are returned.
        System.out.println("JobInstanceCount: " + count);

        // All job names known to the batch runtime can be obtained like so:
        System.out.println(jobOperator.getJobNames());

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

        // <4> Job should be completed.
        System.out.println(jobExecution.getBatchStatus() == BatchStatus.COMPLETED);

        // You can obtain metadata about the running job:
        final Date createTime = jobExecution.getCreateTime();
        final Date startTime = jobExecution.getStartTime();
        final Date endTime = jobExecution.getEndTime();
        System.out.println(createTime + " " + startTime + " " + endTime);

        // You can restart the job using the JobOperator.restart method:
        // jobOperator.restart(executionId, properties);
        // In this code, the execution ID of the job is used to restart a particular job instance.
        // A new set of properties may be specified when the job is restarted.
        //
        // You can cancel the job using the JobOperator.abandon method:
        // jobOperator.abandon(executionId);
    }
}
