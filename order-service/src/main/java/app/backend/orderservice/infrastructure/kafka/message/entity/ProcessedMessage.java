package app.backend.orderservice.infrastructure.kafka.message.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(
	name = "tbl_processed_message",
	uniqueConstraints = {
		@UniqueConstraint(
			name = "uk_processed_message_event_consumer",
			columnNames = {"event_id", "consumer_name"}
		)
	}
)
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProcessedMessage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 36, nullable = false)
	private String eventId;

	@Column(length = 100, nullable = false)
	private String consumerName;

	@Column(length = 100)
	private String eventType;

	@Column(length = 150)
	private String topicName;

	@Column
	private Integer partitionNo;

	@Column
	private Long offsetNo;

	@Column(nullable = false)
	private LocalDateTime processedAt;

	public static ProcessedMessage create(
		String eventId,
		String consumerName,
		String eventType,
		String topicName,
		Integer partitionNo,
		Long offsetNo
	) {
		return ProcessedMessage.builder()
			.eventId(eventId)
			.consumerName(consumerName)
			.eventType(eventType)
			.topicName(topicName)
			.partitionNo(partitionNo)
			.offsetNo(offsetNo)
			.processedAt(LocalDateTime.now())
			.build();
	}
}