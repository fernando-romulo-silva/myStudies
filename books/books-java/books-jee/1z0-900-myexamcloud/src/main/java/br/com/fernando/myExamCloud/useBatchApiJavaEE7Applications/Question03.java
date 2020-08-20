package br.com.fernando.myExamCloud.useBatchApiJavaEE7Applications;

import java.util.Properties;

import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;

public class Question03 {

    public static final String JOB_NAME = "ProductLoadJob";

    // Given a JSL document describing a batch job:
    /**
     * <pre>
     *     <job id="ProductLoadJob" xmlns=http://xmlns.jcp.org/xml/ns/javaee version="1.0">
     *       <step id="Process Product">
     *         <chunk item-count="10">
     *           <reader ref="ProductReader"/>
     *           <processor ref="ProductProcessor"/>
     *           <writer ref="ProductWriter"/>
     *         </chunk>
     *       </step>
     *     </job>
     * </pre>
     */
    // How do you initiate a batch job?
    //
    // Choice A
    // Get the JobExecution object from BatchRuntime and call its start() method.
    //
    // Choice B
    // Get the JobExecution object from BatchRuntime and set its status to JobStatus.INITIATED.
    //
    // Choice C
    // Get the JobOperator object from BatchRuntime and call its start() method.
    //
    // Choice D
    // Call BatchRunTime.initialize("ProductLoadJob");
    //
    //
    //
    // Note that the mere presence of a job XML file or other batch artifacts (such as ItemReader) doesn't mean that a batch job is automatically
    // started when the application is deployed. A batch job must be initiated explicitly, say, from a servlet or from an Enterprise JavaBeans (EJB) timer
    // or an EJB business method.
    //
    // In our payroll application, we use a servlet (named PayrollJobSubmitterServlet) to submit a batch job.
    // The servlet displays an HTML page that presents to the user a form containing two buttons.
    // When the first button, labeled Calculate Payroll, is clicked, the servlet invokes the startNewBatchJob method:

    public long startNewBatchJob() throws Exception {
	String payrollInputDataFileName = "payrollInputDataFileName";

	JobOperator jobOperator = BatchRuntime.getJobOperator();
	Properties props = new Properties();
	props.setProperty("payrollInputDataFileName", payrollInputDataFileName);
	return jobOperator.start(JOB_NAME, props);
    }

    // The first step is to obtain an instance of JobOperator. This can be done by calling the following:
    //
    // JobOperator jobOperator = BatchRuntime.getJobOperator();
    // The servlet then creates a Properties object and stores the input file name in it. Finally, a new batch job is started by calling the following:
    //
    // jobOperator.start(jobName, properties)
    //
    // The jobname is nothing but the job JSL XML file name (minus the .xml extension).
    // The properties parameter serves to pass any input data to the job.
    // The Properties object (containing the name of the payroll input file) is made available to other batch artifacts
    // (such as ItemReader, ItemProcessor, and so on) through the JobContext interface.
    //
    // The batch runtime assigns a unique ID, called the execution ID, to identify each execution of a job whether it is a freshly
    // submitted job or a restarted job.
    // Many of the JobOperator methods take the execution ID as parameter.
    // Using the execution ID, a program can obtain the current (and past) execution status and other statistics about the job.
    // The JobOperator.start() method returns the execution ID of the job that was started.
}
