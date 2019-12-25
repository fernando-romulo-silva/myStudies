package br.com.fernando.chapter15_batchProcessing.part03_exceptionHandling.listeners;

import javax.batch.api.chunk.listener.SkipReadListener;
import javax.inject.Named;

import br.com.fernando.chapter15_batchProcessing.part03_exceptionHandling.UtilRecorder;

@Named
public class MySkipReadListener implements SkipReadListener {

    @Override
    public void onSkipReadItem(final Exception e) throws Exception {
        UtilRecorder.chunkExceptionsCountDownLatch.countDown();
        System.out.println("MySkipReadListener.onSkipReadItem: " + e.getMessage());
    }
}
