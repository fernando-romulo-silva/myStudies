package br.com.fernando.chapter15_batchProcessing.part05_listeners;

import javax.batch.api.chunk.listener.AbstractItemProcessListener;
import javax.inject.Named;

@Named
public class MyItemProcessorListener extends AbstractItemProcessListener {

    @Override
    public void beforeProcess(final Object item) throws Exception {
        UtilRecorder.countDownLatch.countDown();
        System.out.println("MyItemProcessorListener.beforeProcess: " + item);
    }

    @Override
    public void afterProcess(final Object item, final Object result) throws Exception {
        UtilRecorder.countDownLatch.countDown();
        System.out.println("MyItemProcessorListener.afterProcess: " + item + ", " + result);
    }

    @Override
    public void onProcessError(final Object item, final Exception ex) throws Exception {
        UtilRecorder.countDownLatch.countDown();
        System.out.println("MyItemProcessorListener.onProcessError: " + item + ", " + ex.getLocalizedMessage());
    }
}
