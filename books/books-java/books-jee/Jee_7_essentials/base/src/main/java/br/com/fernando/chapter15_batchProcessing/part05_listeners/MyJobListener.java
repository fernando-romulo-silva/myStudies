package br.com.fernando.chapter15_batchProcessing.part05_listeners;

import javax.batch.api.listener.AbstractJobListener;
import javax.inject.Named;

@Named
public class MyJobListener extends AbstractJobListener {

    @Override
    public void beforeJob() {
        UtilRecorder.countDownLatch.countDown();
        System.out.println("MyJobListener.beforeJob");
    }

    @Override
    public void afterJob() {
        UtilRecorder.countDownLatch.countDown();
        System.out.println("MyJobListener.afterJob");
    }
}
