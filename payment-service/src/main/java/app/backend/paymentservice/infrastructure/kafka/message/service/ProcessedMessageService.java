package app.backend.paymentservice.infrastructure.kafka.message.service;

public interface ProcessedMessageService {

	boolean isProcessed(String eventId, String consumerName);

	boolean markAsProcessed(
		String eventId,
		String consumerName,
		String eventType,
		String topicName,
		Integer partitionNo,
		Long offsetNo
	);
}
