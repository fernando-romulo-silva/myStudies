package br.com.fernando.chapter15_batchProcessing.part09_decision;

import javax.batch.api.AbstractBatchlet;
import javax.inject.Named;

@Named
public class MyBatchlet2 extends AbstractBatchlet {

    @Override
    public String process() {
        System.out.println("Running inside a batchlet 2");

        return "COMPLETED";
    }

}
