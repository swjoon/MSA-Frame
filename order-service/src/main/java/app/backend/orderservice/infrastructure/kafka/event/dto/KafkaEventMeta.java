package app.backend.orderservice.infrastructure.kafka.event.dto;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import app.backend.orderservice.global.constant.TraceConstants;
import app.backend.orderservice.infrastructure.kafka.constants.KafkaHeaderName;
import app.backend.orderservice.infrastructure.kafka.util.KafkaHeaderUtils;

public record KafkaEventMeta(
	String eventId,
	String eventType,
	String traceId,
	String topicName,
	Integer partitionNo,
	Long offsetNo
) {
	public static KafkaEventMeta from(ConsumerRecord<String, String> record) {
		return new KafkaEventMeta(
			KafkaHeaderUtils.getHeaderAsString(record, KafkaHeaderName.EVENT_ID),
			KafkaHeaderUtils.getHeaderAsString(record, KafkaHeaderName.EVENT_TYPE),
			KafkaHeaderUtils.getHeaderAsString(record, TraceConstants.TRACE_ID_HEADER),
			record.topic(),
			record.partition(),
			record.offset()
		);
	}
}