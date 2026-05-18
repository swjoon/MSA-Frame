package app.backend.orderservice.infrastructure.kafka.message.repository;

import app.backend.orderservice.infrastructure.kafka.message.entity.ProcessedMessage;

public interface ProcessedMessageRepository {

	void createProcessedMessage(ProcessedMessage processedMessage);

	boolean existsByEventIdAndConsumerName(String eventId, String consumerName);
}
