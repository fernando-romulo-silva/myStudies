package br.com.fernando.chapter15_batchProcessing.part04_batchletProcessing;

import static java.lang.System.out;
import static javax.batch.runtime.BatchStatus.COMPLETED;

import javax.batch.api.AbstractBatchlet;
import javax.inject.Named;

@Named //CDI object. @Named ensures that this bean can be referenced in Job XML
// MyBatchlet is the implementation of the batchlet step.
public class MyBatchlet extends AbstractBatchlet {

    // The process method is called to perform the work of the batchlet. 
    // If this method throws an exception, the batchlet step ends with a status of FAILED. 
    @Override
    public String process() {
        out.println("Running inside a batchlet");

        // Note, in this case, an explicit status of COMPLETE is returned as the job status. 
        return COMPLETED.toString();
    }
}
