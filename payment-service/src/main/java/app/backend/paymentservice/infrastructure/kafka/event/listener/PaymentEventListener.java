package app.backend.paymentservice.infrastructure.kafka.event.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.MDC;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import app.backend.paymentservice.global.constant.TraceConstants;
import app.backend.paymentservice.infrastructure.kafka.constants.EventTopics;
import app.backend.paymentservice.infrastructure.kafka.event.dto.KafkaEventMeta;
import app.backend.paymentservice.infrastructure.kafka.event.dto.response.PaymentCheckedEvent;
import app.backend.paymentservice.infrastructure.kafka.event.handler.PaymentEventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentEventListener {

	private final ObjectMapper objectMapper;
	private final PaymentEventHandler paymentEventHandler;

	@KafkaListener(
		topics = EventTopics.PAYMENT_CHECK_REQUIRED,
		groupId = "payment-service"
	)
	public void paymentCheckRequired(
		ConsumerRecord<String, String> record,
		Acknowledgment ack
	) throws JsonProcessingException {

		log.info(
			"PaymentCheckRequired 수신. topic={}, partition={}, offset={}",
			record.topic(),
			record.partition(),
			record.offset()
		);

		PaymentCheckedEvent event = objectMapper.readValue(record.value(), PaymentCheckedEvent.class);

		KafkaEventMeta meta = KafkaEventMeta.from(record);

		MDC.put(TraceConstants.TRACE_ID_MDC_KEY, currentTraceId(meta));

		try {
			paymentEventHandler.handlePaymentCheckRequired(
				event,
				meta,
				String.format("%s.%s", EventTopics.CONSUMER, EventTopics.PAYMENT_CHECK_REQUIRED)
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
