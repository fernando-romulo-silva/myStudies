package com.vinsguru.common;

import com.vinsguru.sec06.BankService;
import com.vinsguru.sec06.TransferService;

/*
    a simple class to start the server with specific services for demo purposes
 */
public class Demo2 {

    public static void main(String[] args) {

        GrpcServer.create(6565, new BankService(), new TransferService())
                .start()
                .await();

    }

}
