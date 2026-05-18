package app.backend.itemservice.infrastructure.kafka.message.repository;

import app.backend.itemservice.infrastructure.kafka.message.entity.ProcessedMessage;

public interface ProcessedMessageRepository {

	void createProcessedMessage(ProcessedMessage processedMessage);

	boolean existsByEventIdAndConsumerName(String eventId, String consumerName);
}
