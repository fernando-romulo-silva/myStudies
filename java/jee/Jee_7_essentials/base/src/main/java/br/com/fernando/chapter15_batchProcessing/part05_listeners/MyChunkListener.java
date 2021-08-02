package br.com.fernando.chapter15_batchProcessing.part05_listeners;

import javax.batch.api.chunk.listener.AbstractChunkListener;
import javax.inject.Named;

@Named
public class MyChunkListener extends AbstractChunkListener {

    @Override
    public void beforeChunk() throws Exception {
        UtilRecorder.countDownLatch.countDown();
        System.out.println("MyChunkListener.beforeChunk");
    }

    @Override
    public void afterChunk() throws Exception {
        UtilRecorder.countDownLatch.countDown();
        System.out.println("MyChunkListener.afterChunk");
    }
}
