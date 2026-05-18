package app.backend.paymentservice.infrastructure.kafka.outbox.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_outbox_event")
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OutboxEvent {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(length = 36, nullable = false, updatable = false)
	private String id;

	@Column(length = 100, nullable = false)
	private String aggregateType;

	@Column(length = 100, nullable = false)
	private String aggregateId;

	@Column(length = 100, nullable = false)
	private String eventType;

	@Column(length = 150, nullable = false)
	private String eventTopic;

	@Column(columnDefinition = "json", nullable = false)
	private String payload;

	@Column(length = 100)
	private String traceId;

	@Column(nullable = false)
	private LocalDateTime createdAt;

	public static OutboxEvent create(
		String aggregateType,
		String aggregateId,
		String eventType,
		String eventTopic,
		String payload,
		String traceId
	) {
		return OutboxEvent.builder()
			.aggregateType(aggregateType)
			.aggregateId(aggregateId)
			.eventType(eventType)
			.eventTopic(eventTopic)
			.payload(payload)
			.traceId(traceId)
			.createdAt(LocalDateTime.now())
			.build();
	}
}
