package com.apress.prospring5.ch11;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public interface AsyncService {
    void asyncTask();

    Future<String> asyncWithReturn(String name);

    CompletableFuture<String> asyncWithReturn2(String name);
}
