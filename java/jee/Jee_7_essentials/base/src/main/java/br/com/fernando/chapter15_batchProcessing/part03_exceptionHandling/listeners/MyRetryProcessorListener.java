package br.com.fernando.chapter15_batchProcessing.part03_exceptionHandling.listeners;

import javax.batch.api.chunk.listener.RetryProcessListener;
import javax.inject.Named;

@Named
public class MyRetryProcessorListener implements RetryProcessListener {

    @Override
    public void onRetryProcessException(final Object item, final Exception ex) throws Exception {
        System.out.println("MyRetryProcessorListener.onRetryProcessException");
    }
}
