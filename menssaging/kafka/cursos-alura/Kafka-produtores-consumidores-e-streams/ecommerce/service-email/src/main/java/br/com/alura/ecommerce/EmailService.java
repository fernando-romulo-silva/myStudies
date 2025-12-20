package br.com.alura.ecommerce;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;

import br.com.alura.ecommerce.consumer.ConsumerService;
import br.com.alura.ecommerce.consumer.ServiceRunner;

public class EmailService implements ConsumerService<String> {

    public static void main(String[] args) throws InterruptedException, IOException, ExecutionException {
        new ServiceRunner<>(EmailService::new).starts(5);
    }

    public String getConsumerGroup() {
        return StringDeserializer.class.getName();
    }

    @Override
    public String getTopic() {
        return "ECOMMERCE_SEND_EMAIL";
    }

    @Override
    public void parse(ConsumerRecord<String, Message<String>> record) {
        System.out.println("---------------------");
        System.out.println("Sending email new order, checking for fraud");
        System.out.println(record.key());
        System.out.println(record.value());
        System.out.println(record.partition());
        System.out.println(record.offset());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
    }
}
