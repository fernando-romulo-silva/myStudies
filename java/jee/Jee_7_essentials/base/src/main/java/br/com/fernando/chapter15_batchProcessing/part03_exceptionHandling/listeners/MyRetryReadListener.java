package br.com.fernando.chapter15_batchProcessing.part03_exceptionHandling.listeners;

import javax.batch.api.chunk.listener.RetryReadListener;
import javax.inject.Named;

import br.com.fernando.chapter15_batchProcessing.part03_exceptionHandling.UtilRecorder;

@Named
public class MyRetryReadListener implements RetryReadListener {

    @Override
    public void onRetryReadException(final Exception ex) throws Exception {
        UtilRecorder.retryReadExecutions++;
        UtilRecorder.chunkExceptionsCountDownLatch.countDown();
        System.out.println("MyRetryReadListener.onRetryReadException " + ex.getMessage());
    }
}
