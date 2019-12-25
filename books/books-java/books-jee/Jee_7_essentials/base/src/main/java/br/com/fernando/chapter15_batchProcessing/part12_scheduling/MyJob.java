package br.com.fernando.chapter15_batchProcessing.part12_scheduling;

import javax.batch.runtime.BatchRuntime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MyJob implements Runnable {

    public static List<Long> executedBatchs = new ArrayList<>();

    @Override
    public void run() {
        executedBatchs.add(BatchRuntime.getJobOperator().start("myJob", new Properties()));
        afterRun();
    }

    protected void afterRun() {
        System.out.println("Job submitted");
    }
}
