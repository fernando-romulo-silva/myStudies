package br.com.fernando.chapter15_batchProcessing.part03_exceptionHandling.listeners;

import java.util.List;

import javax.batch.api.chunk.listener.SkipWriteListener;
import javax.inject.Named;

import br.com.fernando.chapter15_batchProcessing.part03_exceptionHandling.UtilRecorder;
import br.com.fernando.chapter15_batchProcessing.part03_exceptionHandling.MyOutputRecord;

@Named
public class MySkipWriteListener implements SkipWriteListener {

    @Override
    public void onSkipWriteItem(@SuppressWarnings("rawtypes") final List list, final Exception e) throws Exception {
        UtilRecorder.chunkExceptionsCountDownLatch.countDown();
        System.out.println("MySkipWriteListener.onSkipWriteItem: " + list.size() + ", " + e.getMessage());
        list.remove(new MyOutputRecord(2));
    }

}
