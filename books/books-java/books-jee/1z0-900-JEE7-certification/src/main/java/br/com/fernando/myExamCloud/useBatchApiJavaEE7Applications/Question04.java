package br.com.fernando.myExamCloud.useBatchApiJavaEE7Applications;

public class Question04 {

    // Which of the following obtain JobOperator?
    //
    // Choice A
    // JobOperator jobOperator = Runtime.getJobOperator();
    //
    // Choice B
    // JobOperator jobOperator = BatchRuntime.getJobOperator();
    //
    // Choice C
    // JobOperator jobOperator = SystemRuntime.getJobOperator();
    //
    // Choice D
    // JobOperator jobOperator = JobOperator.getJobOperator();
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
    // JobOperator jobOperator = BatchRuntime.getJobOperator(); is correct. 
    //
    // The servlet then creates a Properties object and stores the input file name in it. Finally, a new batch job is started by calling the following:
    //
    // jobOperator.start(jobName, properties)
    //
    // The jobname is the job JSL XML file name (minus the .xml extension).
    //
    // The properties parameter serves to pass any input data to the job. 
    // The Properties object (containing the name of the payroll input file) is made available to other batch artifacts (such as ItemReader, ItemProcessor, and so on) through the JobContext interface.
}
