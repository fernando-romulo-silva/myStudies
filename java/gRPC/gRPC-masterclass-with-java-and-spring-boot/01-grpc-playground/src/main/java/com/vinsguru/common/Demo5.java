package com.vinsguru.common;

import com.vinsguru.sec10.BankService;

public class Demo5 {

    public static void main(String[] args) {

        GrpcServer.create(6565, new BankService())
                .start()
                .await();

    }
}
