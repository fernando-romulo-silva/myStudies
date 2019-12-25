package br.com.fernando.chapter15_batchProcessing.part05_listeners;

import java.util.List;

import javax.batch.api.chunk.listener.AbstractItemWriteListener;
import javax.inject.Named;

@Named
@SuppressWarnings("rawtypes")
public class MyItemWriteListener extends AbstractItemWriteListener {

    @Override
    public void beforeWrite(final List items) throws Exception {
        UtilRecorder.countDownLatch.countDown();
        System.out.println("MyItemWriteListener.beforeWrite: " + items);
    }

    @Override
    public void afterWrite(final List items) throws Exception {
        UtilRecorder.countDownLatch.countDown();
        System.out.println("MyItemWriteListener.afterWrite: " + items);
    }

    @Override
    public void onWriteError(final List items, final Exception ex) throws Exception {
        UtilRecorder.countDownLatch.countDown();
        System.out.println("MyItemWriteListener.onError: " + items + ", " + ex.getLocalizedMessage());
    }
}
