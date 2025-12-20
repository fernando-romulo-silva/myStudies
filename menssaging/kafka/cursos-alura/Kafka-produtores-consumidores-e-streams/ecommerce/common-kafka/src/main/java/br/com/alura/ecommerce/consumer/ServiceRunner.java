package br.com.alura.ecommerce.consumer;

import java.util.concurrent.Executors;

public class ServiceRunner<T> {

    private final ServiceProvider<T> serviceProvider;

    public ServiceRunner(final ServiceFactory<T> factory) {
        this.serviceProvider = new ServiceProvider<>(factory);
    }

    public void starts(int threads) {
        final var pool = Executors.newFixedThreadPool(threads);
        for (var i = 0; i <= threads; i++) {
            pool.submit(serviceProvider);
        }
    }
}
