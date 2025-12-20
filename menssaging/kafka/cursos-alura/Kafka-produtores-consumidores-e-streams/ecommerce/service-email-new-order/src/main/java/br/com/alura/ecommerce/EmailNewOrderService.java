package br.com.alura.ecommerce;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import br.com.alura.ecommerce.consumer.ConsumerService;
import br.com.alura.ecommerce.consumer.ServiceRunner;
import br.com.alura.ecommerce.dispatcher.KafkaDispatcher;

public class EmailNewOrderService implements ConsumerService<Order> {

    public static void main(String[] args) throws InterruptedException, ExecutionException, IOException {
        new ServiceRunner<>(EmailNewOrderService::new).starts(1);
    }

    private final KafkaDispatcher<String> emailDispacher = new KafkaDispatcher<>();

    @Override
    public String getTopic() {
        return "ECOMMERCE_NEW_ORDER";
    }

    @Override
    public void parse(ConsumerRecord<String, Message<Order>> record) throws InterruptedException, ExecutionException {
        final var message = record.value();

        System.out.println("---------------------");
        System.out.println("Processing new order, preparing email");
        System.out.println(message);

        final var order = message.getPayload();
        final var correlationId = message.getId().continueWith(EmailNewOrderService.class.getSimpleName());
        final var emailCode = "Welcome! We are processing your order!";

        emailDispacher.send("ECOMMERCE_SEND_EMAIL", order.getEmail(), correlationId, emailCode);
    }

    @Override
    public String getConsumerGroup() {
        return EmailNewOrderService.class.getSimpleName();
    }
}
