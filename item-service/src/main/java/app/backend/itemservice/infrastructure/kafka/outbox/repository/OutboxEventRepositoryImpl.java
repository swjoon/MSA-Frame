package app.backend.itemservice.infrastructure.kafka.outbox.repository;

import org.springframework.stereotype.Repository;

import app.backend.itemservice.infrastructure.kafka.outbox.entity.OutboxEvent;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OutboxEventRepositoryImpl implements OutboxEventRepository {

	private final OutboxEventJpaRepository outboxEventJpaRepository;

	@Override
	public void saveOutboxEvent(final OutboxEvent outboxEvent) {

		outboxEventJpaRepository.save(outboxEvent);
	}
}
