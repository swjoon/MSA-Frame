package app.backend.orderservice.infrastructure.kafka.event.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.MDC;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import app.backend.orderservice.global.constant.TraceConstants;
import app.backend.orderservice.infrastructure.kafka.constants.EventTopics;
import app.backend.orderservice.infrastructure.kafka.event.dto.KafkaEventMeta;
import app.backend.orderservice.infrastructure.kafka.event.dto.response.PaymentApprovedEvent;
import app.backend.orderservice.infrastructure.kafka.event.dto.response.PaymentFailedEvent;
import app.backend.orderservice.infrastructure.kafka.event.dto.response.PaymentUnknownEvent;
import app.backend.orderservice.infrastructure.kafka.event.handler.OrderEventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventListener {

	private final ObjectMapper objectMapper;
	private final OrderEventHandler orderEventHandler;

	@KafkaListener(
		topics = EventTopics.PAYMENT_APPROVED,
		groupId = "order-service"
	)
	public void paymentApproved(
		ConsumerRecord<String, String> record,
		Acknowledgment ack
	) throws JsonProcessingException {

		log.info(
			"PaymentApproved 수신. topic={}, partition={}, offset={}",
			record.topic(),
			record.partition(),
			record.offset()
		);

		PaymentApprovedEvent event =
			objectMapper.readValue(record.value(), PaymentApprovedEvent.class);

		KafkaEventMeta meta = KafkaEventMeta.from(record);

		MDC.put(TraceConstants.TRACE_ID_MDC_KEY, currentTraceId(meta));

		try {
			orderEventHandler.handlePaymentApproved(
				event,
				meta,
				String.format("%s.%s", EventTopics.CONSUMER, EventTopics.PAYMENT_APPROVED)
			);

			ack.acknowledge();

		} finally {
			MDC.remove(TraceConstants.TRACE_ID_MDC_KEY);
		}
	}

	@KafkaListener(
		topics = EventTopics.PAYMENT_FAILED,
		groupId = "order-service"
	)
	public void paymentFailed(
		ConsumerRecord<String, String> record,
		Acknowledgment ack
	) throws JsonProcessingException {

		log.info(
			"PaymentFailed 수신. topic={}, partition={}, offset={}",
			record.topic(),
			record.partition(),
			record.offset()
		);

		PaymentFailedEvent event =
			objectMapper.readValue(record.value(), PaymentFailedEvent.class);

		KafkaEventMeta meta = KafkaEventMeta.from(record);

		MDC.put(TraceConstants.TRACE_ID_MDC_KEY, currentTraceId(meta));

		try {
			orderEventHandler.handlePaymentFailed(
				event,
				meta,
				String.format("%s.%s", EventTopics.CONSUMER, EventTopics.PAYMENT_FAILED)
			);

			ack.acknowledge();

		} finally {
			MDC.remove(TraceConstants.TRACE_ID_MDC_KEY);
		}
	}

	@KafkaListener(
		topics = EventTopics.PAYMENT_UNKNOWN,
		groupId = "order-service"
	)
	public void paymentUnknown(
		ConsumerRecord<String, String> record,
		Acknowledgment ack
	) throws JsonProcessingException {

		log.info(
			"PaymentUnknown 수신. topic={}, partition={}, offset={}",
			record.topic(),
			record.partition(),
			record.offset()
		);

		PaymentUnknownEvent event =
			objectMapper.readValue(record.value(), PaymentUnknownEvent.class);

		KafkaEventMeta meta = KafkaEventMeta.from(record);

		MDC.put(TraceConstants.TRACE_ID_MDC_KEY, currentTraceId(meta));

		try {
			orderEventHandler.handlePaymentUnknown(
				event,
				meta,
				String.format("%s.%s", EventTopics.CONSUMER, EventTopics.PAYMENT_UNKNOWN)
			);

			ack.acknowledge();

		} finally {
			MDC.remove(TraceConstants.TRACE_ID_MDC_KEY);
		}
	}

	private String currentTraceId(KafkaEventMeta meta) {
		if (meta.traceId() == null || meta.traceId().isBlank()) {
			return TraceConstants.NO_TRACE;
		}

		return meta.traceId();
	}
}
