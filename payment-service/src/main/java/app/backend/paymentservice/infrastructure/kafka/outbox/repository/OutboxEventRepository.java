package app.backend.paymentservice.infrastructure.kafka.outbox.repository;

import app.backend.paymentservice.infrastructure.kafka.outbox.entity.OutboxEvent;

public interface OutboxEventRepository {

	void saveOutboxEvent(OutboxEvent outboxEvent);

}
