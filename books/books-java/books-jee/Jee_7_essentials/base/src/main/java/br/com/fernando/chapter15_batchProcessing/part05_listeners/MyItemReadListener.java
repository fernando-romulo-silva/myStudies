package br.com.fernando.chapter15_batchProcessing.part05_listeners;

import javax.batch.api.chunk.listener.AbstractItemReadListener;
import javax.inject.Named;

@Named
public class MyItemReadListener extends AbstractItemReadListener {

    @Override
    public void beforeRead() throws Exception {
        UtilRecorder.countDownLatch.countDown();
        System.out.println("MyItemReadListener.beforeRead");
    }

    @Override
    public void afterRead(final Object item) throws Exception {
        UtilRecorder.countDownLatch.countDown();
        System.out.println("MyItemReadListener.afterRead: " + item);
    }

    @Override
    public void onReadError(final Exception ex) throws Exception {
        UtilRecorder.countDownLatch.countDown();
        System.out.println("MyItemReadListener.onReadError: " + ex.getLocalizedMessage());
    }
}
