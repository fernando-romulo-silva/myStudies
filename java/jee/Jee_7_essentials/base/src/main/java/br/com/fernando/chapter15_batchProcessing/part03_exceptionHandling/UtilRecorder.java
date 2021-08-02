package br.com.fernando.chapter15_batchProcessing.part03_exceptionHandling;

import java.util.concurrent.CountDownLatch;

public class UtilRecorder {

    public static CountDownLatch chunkExceptionsCountDownLatch = new CountDownLatch(3);

    public static int retryReadExecutions = 0;
}
