package br.com.fernando.enthuware.useBatchApiJavaEE7Applications;

public class Question02 {

    // Given a JSL document describing a batch job:
    /**
     * <pre>
     *      <job id="PayrollBatchJob" xmlns="http://xmlns.jcp.org/xml/ns/javaee" version="1.0">
     *          <step id="ProcessProduct">
     *              <chunk item-count="10">
     *                   <reader ref="ProductReader"/>
     *                   <processor ref="ProductProcessor"/>
     *                   <writer ref="ProductWriter"/>
     *               </chunk>
     *           </step>
     *      </job>
     * </pre>
     */
    // How do you initiate a batch job?
    //
    // A - Get the JobExecution object from BatchRuntime and call its start() method.
    //
    // B - Get the JobExecution object from BatchRuntime and set its status to JobStatus.INITIATED.
    //
    // C - Get the JobOperator object from BatchRuntime and call its start() method.
    //
    // D - Call BatchRunTime.initialize("ProductLoadJob");
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
    // The correct Answer C    
}
