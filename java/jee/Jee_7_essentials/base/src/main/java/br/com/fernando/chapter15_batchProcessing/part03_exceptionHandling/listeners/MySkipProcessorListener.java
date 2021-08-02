package br.com.fernando.chapter15_batchProcessing.part03_exceptionHandling.listeners;

import javax.batch.api.chunk.listener.SkipProcessListener;
import javax.inject.Named;

import br.com.fernando.chapter15_batchProcessing.part03_exceptionHandling.UtilRecorder;
import br.com.fernando.chapter15_batchProcessing.part03_exceptionHandling.MyInputRecord;

@Named
public class MySkipProcessorListener implements SkipProcessListener {

    @Override
    public void onSkipProcessItem(final Object t, final Exception e) throws Exception {
        UtilRecorder.chunkExceptionsCountDownLatch.countDown();
        System.out.println("MySkipProcessorListener.onSkipProcessItem: " + ((MyInputRecord) t).getId() + ", " + e.getMessage());
    }
}
