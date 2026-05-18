package app.backend.itemservice.infrastructure.kafka.outbox.service;

public interface OutboxEventService {

	void publish(String aggregateType, String aggregateId, String eventType, String eventTopic, Object payload);
}
