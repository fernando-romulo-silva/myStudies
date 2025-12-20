package br.com.alura.ecommerce;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import br.com.alura.ecommerce.consumer.KafkaService;
import br.com.alura.ecommerce.dispatcher.KafkaDispatcher;

public class FraudDetectorService {

    public static void main(String[] args) throws InterruptedException, ExecutionException, IOException {

        final var fraudDetectorService = new FraudDetectorService();
        try (final var service = new KafkaService<Order>("ECOMMERCE_NEW_ORDER", fraudDetectorService::parse,
                FraudDetectorService.class.getSimpleName(), Map.of())) {
            service.run();
        }
    }

    private final KafkaDispatcher<Order> ordeDispatcher = new KafkaDispatcher<>();

    private void parse(ConsumerRecord<String, Message<Order>> record) throws InterruptedException, ExecutionException {
        System.out.println("---------------------");
        System.out.println("Processing new order, checking for fraud");
        System.out.println(record.key());
        System.out.println(record.value());
        System.out.println(record.partition());
        System.out.println(record.offset());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        final var message = record.value();
        final var order = message.getPayload();
        final var amount = order.getAmount();
        final var correlationId = message.getId().continueWith(FraudDetectorService.class.getSimpleName());

        if (isFraud(amount)) {
            // pretending that the fraud happens when the amount is >= 4500
            System.out.println("Order is a fraud: " + order);
            ordeDispatcher.send("ECOMMERCE_ORDER_REJECTED", order.getEmail(), correlationId, order);
        } else {
            System.out.println("Approved: " + order);
            ordeDispatcher.send("ECOMMERCE_ORDER_APPROVED", order.getEmail(), correlationId, order);
        }
    }

    private boolean isFraud(final BigDecimal amount) {
        return amount.compareTo(new BigDecimal("4500")) >= 0;
    }
}
