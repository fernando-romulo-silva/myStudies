package br.com.fernando.chapter15_batchProcessing.part05_listeners;

import javax.batch.api.listener.AbstractStepListener;
import javax.inject.Named;

@Named
public class MyStepListener extends AbstractStepListener {

    @Override
    public void beforeStep() throws Exception {
        UtilRecorder.countDownLatch.countDown();
        System.out.println("MyStepListener.beforeStep");
    }

    @Override
    public void afterStep() throws Exception {
        UtilRecorder.countDownLatch.countDown();
        System.out.println("MyStepListener.afterStep");
    }
}
