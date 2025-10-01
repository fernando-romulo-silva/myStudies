package com.vinsguru.common;

import com.vinsguru.sec06.BankService;

import io.grpc.ServerBuilder;

public class GrpcServerSimple {

    public static void main(String[] args) throws Exception {
        final var server = ServerBuilder.forPort(6565)
                .addService(new BankService())
                .build();

        server.start();
        server.awaitTermination();
    }
}
