package br.com.fernando.chapter15_batchProcessing.part08_split;

import javax.batch.api.AbstractBatchlet;
import javax.inject.Named;

@Named
public class MyBatchlet1 extends AbstractBatchlet {

    @Override
    public String process() {
        System.out.println("Running inside a batchlet 1");

        return "COMPLETED";
    }

}
