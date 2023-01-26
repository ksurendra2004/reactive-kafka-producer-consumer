package com.reactivekafka.service;

import com.reactivekafka.model.ProducerModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReactiveProducerService {

    private final ReactiveKafkaProducerTemplate<String, ProducerModel> reactiveKafkaProducerTemplate;

    @Value(value = "${PRODUCER_TOPIC}")
    private String topic;

    public void send(ProducerModel producerModel) {
        log.info("send to topic={}, {}={},",
                topic,
                ProducerModel.class.getSimpleName(),
                producerModel);

        reactiveKafkaProducerTemplate.send(topic, producerModel)
                .doOnSuccess(senderResult -> log.info("sent {} offset : {}",
                        producerModel,
                        senderResult.recordMetadata().offset()))
                .subscribe();
    }

}
