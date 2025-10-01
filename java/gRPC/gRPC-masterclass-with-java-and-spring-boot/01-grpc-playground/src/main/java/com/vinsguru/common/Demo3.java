package com.vinsguru.common;

import com.vinsguru.sec07.FlowControlService;

/*
    a simple class to start the server with specific services for demo purposes
 */
public class Demo3 {

    public static void main(String[] args) {

        GrpcServer.create(6565, new FlowControlService())
                .start()
                .await();

    }

}
