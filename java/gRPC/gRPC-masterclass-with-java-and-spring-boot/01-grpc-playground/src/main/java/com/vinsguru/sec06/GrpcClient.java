package com.vinsguru.sec06;

import java.time.Duration;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vinsguru.models.sec06.BalanceCheckRequest;
import com.vinsguru.models.sec06.BankServiceGrpc;
import com.vinsguru.models.sec06.AccountBalance;

import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

public class GrpcClient {

    private static final Logger log = LoggerFactory.getLogger(GrpcClient.class);

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        final var channel = ManagedChannelBuilder.forAddress("localhost", 6565)
                .usePlaintext()
                .build();

        final var stub1 = BankServiceGrpc.newBlockingStub(channel);
        final var balance1 = stub1.getAccountBalance(BalanceCheckRequest.newBuilder().setAccountNumber(2).build());
        log.info("balance 1 {}", balance1);

        final var stub2 = BankServiceGrpc.newStub(channel);
        stub2.getAccountBalance(BalanceCheckRequest.newBuilder().setAccountNumber(2).build(),
                new StreamObserver<AccountBalance>() {

                    @Override
                    public void onNext(AccountBalance value) {
                        log.info("balance 2 {}", value);
                    }

                    @Override
                    public void onError(Throwable t) {
                        log.info("balance 2 error", t);
                    }

                    @Override
                    public void onCompleted() {
                        log.info("balance 2 completed");
                    }
                });

        final var stub3 = BankServiceGrpc.newFutureStub(channel);
        final var balance3Future = stub3
                .getAccountBalance(BalanceCheckRequest.newBuilder().setAccountNumber(2).build());
        final var balance3 = balance3Future.get();
        log.info("balance 3 {}", balance3);

        log.info("Main thread done");
        Thread.sleep(Duration.ofSeconds(2));
    }
}
