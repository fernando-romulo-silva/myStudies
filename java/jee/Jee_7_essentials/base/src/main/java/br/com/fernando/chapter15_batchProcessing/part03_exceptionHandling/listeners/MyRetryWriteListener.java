package br.com.fernando.chapter15_batchProcessing.part03_exceptionHandling.listeners;

import java.util.List;

import javax.batch.api.chunk.listener.RetryWriteListener;
import javax.inject.Named;

@Named
public class MyRetryWriteListener implements RetryWriteListener {

    @Override
    public void onRetryWriteException(final List<Object> items, final Exception ex) throws Exception {
        System.out.println("MyRetryWriteListener.onRetryWriteException");
    }
}
