package com.vinsguru.common;

import com.vinsguru.sec09.BankService;

/*
    a simple class to start the server with specific services for demo purposes
 */
public class Demo4 {

    public static void main(String[] args) {

        GrpcServer.create(6565, new BankService())
                .start()
                .await();

    }

}
