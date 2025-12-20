package br.com.alura.ecommerce.dispatcher;

import java.io.Closeable;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import br.com.alura.ecommerce.CorrelationId;
import br.com.alura.ecommerce.Message;

public class KafkaDispatcher<T> implements Closeable {

    private final KafkaProducer<String, Message<T>> producer;

    public KafkaDispatcher() {
        this.producer = new KafkaProducer<>(properties());
    }

    private static Properties properties() {
        final var properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9093");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, GsonSerializer.class.getName());
        properties.setProperty(ProducerConfig.ACKS_CONFIG, "all");
        return properties;

    }

    public void send(String topic, String key, CorrelationId correlationId, T payload)
            throws InterruptedException, ExecutionException {
        Future<RecordMetadata> send = sendAsync(topic, key, correlationId, payload);
        send.get();
    }

    public Future<RecordMetadata> sendAsync(String topic, String key, CorrelationId correlationId, T payload) {
        final var value = new Message<>(correlationId.continueWith("_" + topic), payload);
        final var record = new ProducerRecord<>(topic, key, value);
        final var callback = (Callback) (data, ex) -> {
            if (ex != null) {
                ex.printStackTrace();
                return;
            }
            System.out.println(
                    "Sucesso enviando " + data.topic() + ":::" + data.partition() + "/" + data.offset() + "/"
                            + data.timestamp());
        };

        return producer.send(record, callback);
    }

    @Override
    public void close() throws IOException {
        producer.close();
    }
}
