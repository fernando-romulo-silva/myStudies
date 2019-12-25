package br.com.fernando.chapter15_batchProcessing.part06_jobSequence;

public class JobSequence {

    // A step is a basic execution element that encapsulates an independent, sequential phase of a job.
    //
    // A job may contain any number of steps. 
    // Each step may be either a chunk type step or batchlet type step. 
    // By default, each step is the last step in the job. 
    // The next step in the job execution sequence needs to be explicitly specified via the next attribute

    /**
     * <pre>
     * 
     * <job id="myJob" xmlns="http://xmlns.jcp.org/xml/ns/javaee" version="1.0">
     *   
     *   <step id="step1" next="step2">
     *     <chunk item-count="3">
     *       <reader ref="myItemReader"></reader>
     *       <processor ref="myItemProcessor"></processor>
     *       <writer ref="myItemWriter"></writer>
     *     </chunk>
     *   </step>
     *   
     *   <step id="step2" >
     *     <batchlet ref="myBatchlet"/>
     *   </step>
     *   
     * </job>
     * </pre>
     */

    // In this Job XML:
    //
    // * We define a job using two steps with the logical names step1 and step2
    //
    // * step1 is defined as a chunk type step and step2 is defined as a batchlet type step.
    //
    // * step1 is executed first and then followed by step2.
    //
    // The order of steps is identified by the next attribute on step1. 
    // step2 is the last step in the job; this is the default.
    //
    //
    // Other than the step, the specification outlines other execution elements that define the sequence of a job:
    //
    // Flow : Defines a sequence of execution elements that execute together as a unit.
    //
    // Split : Defines a set of flows that execute concurrently.
    //
    // Decision : Provides a customized way of determining sequencing among steps, flows, and splits
}
