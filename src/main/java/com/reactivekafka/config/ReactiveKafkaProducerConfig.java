package com.reactivekafka.config;

import com.reactivekafka.model.ProducerModel;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import reactor.kafka.sender.SenderOptions;


@Configuration
public class ReactiveKafkaProducerConfig {

    @Bean
    public SenderOptions<String, ProducerModel> producerProps(KafkaProperties kafkaProperties) {
        return SenderOptions.create(kafkaProperties.buildProducerProperties());
    }
    @Bean
    public ReactiveKafkaProducerTemplate<String, ProducerModel> reactiveKafkaProducerTemplate(
            SenderOptions<String, ProducerModel> producerProps) {
        return new ReactiveKafkaProducerTemplate<>(producerProps);
    }
}
