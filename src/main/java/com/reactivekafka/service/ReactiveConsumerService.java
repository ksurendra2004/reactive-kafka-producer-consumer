package com.reactivekafka.service;

import com.reactivekafka.model.ConsumerModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReactiveConsumerService {

    private final ReactiveKafkaConsumerTemplate<String, ConsumerModel> reactiveKafkaConsumerTemplate;

    public Flux<ConsumerModel> consumeAppUpdates() {
        log.info("In consumeAppUpdates()");
        return reactiveKafkaConsumerTemplate
                .receiveAutoAck()
                .doOnNext(consumerRecord -> log.info("received key={}, value={} from topic={}, offset={}",
                        consumerRecord.key(),
                        consumerRecord.value(),
                        consumerRecord.topic(),
                        consumerRecord.offset())
                )
                .map(ConsumerRecord::value)
                .doOnNext(consumerModel -> log.info("successfully consumed {}={}",
                        ConsumerModel.class.getSimpleName(),
                        consumerModel))
                .doOnError(throwable -> log.error("something bad happened while consuming : {}",
                        throwable.getMessage()));
    }

    @PostConstruct
    public void init() {
        log.info("In init()");
        this.consumeAppUpdates().subscribe();
    }

}
