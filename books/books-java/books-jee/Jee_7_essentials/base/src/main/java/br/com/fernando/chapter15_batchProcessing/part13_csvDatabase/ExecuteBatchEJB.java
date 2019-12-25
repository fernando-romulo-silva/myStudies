package br.com.fernando.chapter15_batchProcessing.part13_csvDatabase;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.JobExecution;
import javax.batch.runtime.Metric;
import javax.batch.runtime.StepExecution;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.com.fernando.utils.BatchTestHelper;

@Stateless
public class ExecuteBatchEJB {

    @PersistenceContext
    private EntityManager entityManager;

    public void doIt() throws Exception {

        final JobOperator jobOperator = BatchRuntime.getJobOperator();

        Long executionId = jobOperator.start("myJob", new Properties());
        JobExecution jobExecution = jobOperator.getJobExecution(executionId);

        jobExecution = BatchTestHelper.keepTestAlive(jobExecution);

        final List<StepExecution> stepExecutions = jobOperator.getStepExecutions(executionId);

        for (StepExecution stepExecution : stepExecutions) {

            if (stepExecution.getStepName().equals("myStep")) {

                final Map<Metric.MetricType, Long> metricsMap = BatchTestHelper.getMetricsMap(stepExecution.getMetrics());

                // <1> The read count should be 7 elements. Check +MyItemReader+.
                System.out.println(metricsMap.get(Metric.MetricType.READ_COUNT).longValue()); // 7L

                // <2> The write count should be the same 7 read elements.
                System.out.println(metricsMap.get(Metric.MetricType.WRITE_COUNT).longValue());

                // <3> The commit count should be 4. Checkpoint is on every 3rd read, 4 commits for read elements.
                System.out.println(metricsMap.get(Metric.MetricType.COMMIT_COUNT).longValue());
            }
        }

        final Query query = entityManager.createNamedQuery("Person.findAll");

        @SuppressWarnings("unchecked")
        final List<Person> persons = query.getResultList();

        // <4> Confirm that the elements were actually persisted into the database.
        System.out.println(persons.size());

        // <5> Job should be completed.
        System.out.println(jobExecution.getBatchStatus());
    }
}
