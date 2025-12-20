package br.com.alura.ecommerce.consumer;

import java.io.Closeable;
import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import br.com.alura.ecommerce.Message;
import br.com.alura.ecommerce.dispatcher.GsonSerializer;
import br.com.alura.ecommerce.dispatcher.KafkaDispatcher;

public class KafkaService<T> implements Closeable {

    private final KafkaConsumer<String, Message<T>> consumer;
    private final ConsumerFunction<T> parse;
    private final String groupId;

    private KafkaService(
            final ConsumerFunction<T> parse, final String groupId, final Map<String, String> config) {
        this.groupId = groupId;
        this.consumer = new KafkaConsumer<>(getProperties(config));
        this.parse = parse;
    }

    public KafkaService(final String topic, ConsumerFunction<T> parse, final String groupId,
            final Map<String, String> config) {
        this(parse, groupId, config);
        consumer.subscribe(Collections.singleton(topic));
    }

    public KafkaService(
            final Pattern pattern, final ConsumerFunction<T> parse,
            final String groupId, final Map<String, String> config) {
        this(parse, groupId, config);
        consumer.subscribe(pattern);
    }

    public void run() throws InterruptedException, ExecutionException, IOException {

        try (final var deadLetter = new KafkaDispatcher<>()) {

            while (true) {
                final var records = this.consumer.poll(Duration.ofMillis(100));
                if (!records.isEmpty()) {
                    System.out.println("Encontrei registros");
                }

                for (final var record : records) {
                    try {
                        parse.consume(record);
                    } catch (Exception e) {
                        final var message = record.value();
                        deadLetter.send("ECOMMERCE_DEADLETTER", message.getId().toString(),
                                message.getId().continueWith("DeadLetter"),
                                new GsonSerializer().serialize("", message));
                    }
                }
            }

        }
    }

    private Properties getProperties(final Map<String, String> overrideProperties) {
        final var properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9093");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, GsonDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.setProperty(ConsumerConfig.CLIENT_ID_CONFIG, UUID.randomUUID().toString());
        properties.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "1");
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "lastest");
        properties.putAll(overrideProperties);
        return properties;
    }

    @Override
    public void close() throws IOException {
        this.consumer.close();
    }

}
