package app.backend.itemservice.infrastructure.kafka.outbox.repository;

import app.backend.itemservice.infrastructure.kafka.outbox.entity.OutboxEvent;

public interface OutboxEventRepository {

	void saveOutboxEvent(OutboxEvent outboxEvent);

}
