package br.com.fernando.chapter15_batchProcessing.part12_scheduling;

import static javax.batch.runtime.BatchStatus.COMPLETED;

import javax.batch.api.AbstractBatchlet;
import javax.inject.Named;

@Named
public class MyBatchlet extends AbstractBatchlet {

    @Override
    public String process() {
        System.out.println("Running inside a batchlet");

        return COMPLETED.toString();
    }
}
