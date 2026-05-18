package app.backend.orderservice.infrastructure.kafka.outbox.repository;

import app.backend.orderservice.infrastructure.kafka.outbox.entity.OutboxEvent;

public interface OutboxEventRepository {

	void saveOutboxEvent(OutboxEvent outboxEvent);

}
