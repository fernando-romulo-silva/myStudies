package br.com.alura.ecommerce.consumer;

import java.util.Map;
import java.util.concurrent.Callable;

public class ServiceProvider<T> implements Callable<Void> {

    private final ServiceFactory<T> factory;

    public ServiceProvider(final ServiceFactory<T> factory) {
        this.factory = factory;
    }

    @Override
    public Void call() throws Exception {

        final var consumerService = factory.create();

        try (final var service = new KafkaService<T>(
                consumerService.getTopic(),
                consumerService::parse,
                consumerService.getConsumerGroup(),
                Map.of())) {
            System.out.println("Starts to run");
            service.run();
        } catch (Exception ex) {
            System.out.println("Error " + ex.getMessage());
        }

        return null;
    }

}
