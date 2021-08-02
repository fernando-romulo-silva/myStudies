package br.com.fernando.chapter15_batchProcessing.part09_decision;

import javax.batch.api.Decider;
import javax.batch.runtime.StepExecution;
import javax.inject.Named;

@Named
public class MyDecider implements Decider {

    // The decide method receives an array of StepExecution objects as input. 
    // These objects represent the execution element that transitions to this decider.

    @Override
    public String decide(final StepExecution[] ses) throws Exception {

        for (final StepExecution se : ses) {
            System.out.println(se.getStepName() + " " + se.getBatchStatus().toString() + " " + se.getExitStatus());
        }

        // The decider method returns an exit status that updates the current job execution’s exit status. 
        return "foobar";
    }

}
