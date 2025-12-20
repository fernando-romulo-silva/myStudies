package br.com.alura.ecommerce;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import br.com.alura.ecommerce.dispatcher.KafkaDispatcher;

public class NewOrderMain {

    public static void main(String[] args) throws InterruptedException, ExecutionException, IOException {
        try (final var orderDispatcher = new KafkaDispatcher<Order>()) {

            final var email = Math.random() + "@email.com";

            for (var i = 0; i < 10; i++) {

                final var orderId = UUID.randomUUID().toString();
                final var amount = new BigDecimal(Math.random() * 5000 + 1);
                final var order = new Order(orderId, amount, email);

                final var correlationId = new CorrelationId(NewOrderMain.class.getSimpleName());
                orderDispatcher.send("ECOMMERCE_NEW_ORDER", email, correlationId, order);
            }
        }
    }

}
