package com.alura.pix.config;

import com.alura.pix.dto.PixDTO;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ProducerKafkaConfig {

        @Value(value = "${spring.kafka.bootstrap-servers:localhost:9092}")
        private String bootstrapAddress;

        @Bean
        public ProducerFactory<String, PixDTO> producerFactory() {
                Map<String, Object> configProps = new HashMap<>();
                configProps.put(
                                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                                bootstrapAddress);
                configProps.put(
                                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                                StringSerializer.class);
                configProps.put(
                                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                                JsonSerializer.class);
                return new DefaultKafkaProducerFactory<>(configProps);
        }

        @Bean
        public KafkaTemplate<String, PixDTO> kafkaTemplate() {
                return new KafkaTemplate<>(producerFactory());
        }

        @Bean
        public ConsumerFactory<String, String> consumerFactory() {
                Map<String, Object> props = new HashMap<>();
                props.put(
                                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                                bootstrapAddress);
                props.put(
                                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                                StringDeserializer.class);
                props.put(
                                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                                JsonDeserializer.class);
                props.put(
                                JsonDeserializer.TRUSTED_PACKAGES,
                                "*");

                // configs

                props.put(
                                ConsumerConfig.MAX_POLL_RECORDS_CONFIG,
                                100); // default 500

                props.put(
                                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,
                                "earliest"); // default "latest"

                props.put(
                                ConsumerConfig.ALLOW_AUTO_CREATE_TOPICS_CONFIG,
                                false); // default true

                props.put(
                                ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,
                                false); // default true

                return new DefaultKafkaConsumerFactory<>(props);
        }

        @Bean
        public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {

                ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
                factory.setConsumerFactory(consumerFactory());
                factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
                return factory;
        }
}
