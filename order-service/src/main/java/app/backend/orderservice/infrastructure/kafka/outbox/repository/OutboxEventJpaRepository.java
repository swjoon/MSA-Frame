package app.backend.orderservice.infrastructure.kafka.outbox.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.backend.orderservice.infrastructure.kafka.outbox.entity.OutboxEvent;

public interface OutboxEventJpaRepository extends JpaRepository<OutboxEvent, Long> {
}
