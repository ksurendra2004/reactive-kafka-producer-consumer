package com.reactivekafka.config;

import com.reactivekafka.model.ConsumerModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import reactor.kafka.receiver.ReceiverOptions;

import java.util.Collections;

@Configuration
@Slf4j
public class ReactiveKafkaConsumerConfig {

    @Bean
    public ReceiverOptions<String, ConsumerModel> kafkaReceiverOptions(
            @Value("${CONSUMER_TOPIC}") String topic,
            KafkaProperties kafkaProperties) {

        ReceiverOptions<String, ConsumerModel> basicReceiverOptions =
                ReceiverOptions.create(kafkaProperties.buildConsumerProperties());

        return basicReceiverOptions.subscription(Collections.singletonList(topic))
                .addAssignListener(partitions -> log.info("onPartitionsAssigned {}", partitions))
                .addRevokeListener(partitions -> log.info("onPartitionsRevoked {}", partitions));
    }

    @Bean
    public ReactiveKafkaConsumerTemplate<String, ConsumerModel> reactiveKafkaConsumerTemplate(
            ReceiverOptions<String, ConsumerModel> kafkaReceiverOptions) {
        return new ReactiveKafkaConsumerTemplate<>(kafkaReceiverOptions);
    }
}
