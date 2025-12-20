package br.com.alura.ecommerce;

import java.util.Map;
import java.util.regex.Pattern;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;

import br.com.alura.ecommerce.consumer.KafkaService;

public class LogService {

    public static void main(String[] args) throws Exception {

        final var logService = new LogService();

        try (final var service = new KafkaService<>(
                Pattern.compile("ECOMMERCE.*"),
                logService::parse,
                LogService.class.getSimpleName(),
                Map.of(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName()))) {
            service.run();
        }
    }

    private void parse(ConsumerRecord<String, Message<String>> record) {
        System.out.println("---------------------");
        System.out.println("LOG " + record.topic());

        System.out.println(record.key());
        System.out.println(record.value());
        System.out.println(record.partition());
        System.out.println(record.offset());
    }
}
