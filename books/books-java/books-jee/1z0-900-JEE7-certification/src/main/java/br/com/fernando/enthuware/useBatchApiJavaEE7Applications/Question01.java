package br.com.fernando.enthuware.useBatchApiJavaEE7Applications;

public class Question01 {

    // Given the code fragment:
    /**
     * <pre>
     *      <job id="PayrollBatchJob" xmlns="http://xmlns.jcp.org/xml/ns/javaee" version="1.0">
     *          <step id="process">
     *              <chunk item-count="10">
     *                   <reader ref="reader"/>
     *                   <processor ref="processor"/>
     *                   <writer ref="writer"/>
     *               </chunk>
     *           </step>
     *      </job>
     * </pre>
     */
    // How can you send an email to a payroll administrator at the conclusion of this batch job?
    //
    // A - Define an <end> tag at the end of the current <step> tag and reference a class that sends an email.
    //
    // B - Configure the processor class to send an email.
    //
    // C - Define another <chunk> element at the end of this step with a <processor> subelement, which references a class that sends an email.
    //
    // D - Add another <step> element defining a <batchlet> subelement, which references a class that sends an email.
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
    //
    //
    // The correct Answer D
    
    /**
     * <pre>
     *      <job id="PayrollBatchJob" xmlns="http://xmlns.jcp.org/xml/ns/javaee" version="1.0">
     *          <step id="process">
     *              <chunk item-count="10">
     *                   <reader ref="reader"/>
     *                   <processor ref="processor"/>
     *                   <writer ref="writer"/>
     *               </chunk>
     *           </step>
     *           <step id="email">
     *           	<batchlet ref="emailBatchLet" />
     *           </step>
     *      </job>
     * </pre>
     */

}
