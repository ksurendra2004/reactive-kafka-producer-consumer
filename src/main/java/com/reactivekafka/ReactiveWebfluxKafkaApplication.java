package com.reactivekafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

@SpringBootApplication
public class ReactiveWebfluxKafkaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReactiveWebfluxKafkaApplication.class, args);
	}

	@Bean
	public NewTopic producerTopic() {
		return TopicBuilder.name("prod_topic")
				.partitions(3)
				.compact()
				.build();
	}

	@Bean
	public NewTopic consumerTopic() {
		return TopicBuilder.name("con_topic")
				.partitions(3)
				.compact()
				.build();
	}

}
