package app.backend.itemservice.infrastructure.kafka.message.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.backend.itemservice.infrastructure.kafka.message.entity.ProcessedMessage;

public interface ProcessedMessageJpaRepository extends JpaRepository<ProcessedMessage, Long> {

	boolean existsByEventIdAndConsumerName(String eventId, String consumerName);

}
