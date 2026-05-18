package app.backend.paymentservice.infrastructure.kafka.outbox.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.backend.paymentservice.infrastructure.kafka.outbox.entity.OutboxEvent;

public interface OutboxEventJpaRepository extends JpaRepository<OutboxEvent, Long> {
}
