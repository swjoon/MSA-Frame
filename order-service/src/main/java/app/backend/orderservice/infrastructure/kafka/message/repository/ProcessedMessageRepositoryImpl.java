package app.backend.orderservice.infrastructure.kafka.message.repository;

import org.springframework.stereotype.Repository;

import app.backend.orderservice.infrastructure.kafka.message.entity.ProcessedMessage;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProcessedMessageRepositoryImpl implements ProcessedMessageRepository {

	private final ProcessedMessageJpaRepository jpaRepository;

	@Override
	public void createProcessedMessage(final ProcessedMessage processedMessage) {

		jpaRepository.save(processedMessage);
	}

	@Override
	public boolean existsByEventIdAndConsumerName(final String eventId, final String consumerName) {

		return jpaRepository.existsByEventIdAndConsumerName(eventId, consumerName);
	}
}
