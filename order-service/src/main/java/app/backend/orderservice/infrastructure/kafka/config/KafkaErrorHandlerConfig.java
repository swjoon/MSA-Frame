package app.backend.orderservice.infrastructure.kafka.config;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class KafkaErrorHandlerConfig {

	@Bean
	public DefaultErrorHandler defaultErrorHandler() {
		FixedBackOff fixedBackOff = new FixedBackOff(1000L, 3L);

		return new DefaultErrorHandler(
			(ConsumerRecord<?, ?> record, Exception exception) -> {
				log.error(
					"Kafka message processing failed. topic={}, partition={}, offset={}, key={}, value={}",
					record.topic(),
					record.partition(),
					record.offset(),
					record.key(),
					record.value(),
					exception
				);
			},
			fixedBackOff
		);
	}
}