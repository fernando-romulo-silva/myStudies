package com.vinsguru.common;

import com.vinsguru.sec12.BankService;

public class Demo7 {

    public static void main(String[] args) {

        GrpcServer.create(6565, new BankService())
                .start()
                .await();

    }
}
