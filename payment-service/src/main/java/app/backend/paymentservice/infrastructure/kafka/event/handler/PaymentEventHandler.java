package app.backend.paymentservice.infrastructure.kafka.event.handler;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import app.backend.paymentservice.infrastructure.client.payment.dto.response.PaymentResponse;
import app.backend.paymentservice.infrastructure.client.payment.gateway.PaymentGateway;
import app.backend.paymentservice.infrastructure.kafka.constants.EventTopics;
import app.backend.paymentservice.infrastructure.kafka.event.dto.KafkaEventMeta;
import app.backend.paymentservice.infrastructure.kafka.event.dto.request.PaymentApprovedEvent;
import app.backend.paymentservice.infrastructure.kafka.event.dto.request.PaymentFailedEvent;
import app.backend.paymentservice.infrastructure.kafka.event.dto.request.PaymentUnknownEvent;
import app.backend.paymentservice.infrastructure.kafka.event.dto.response.PaymentCheckedEvent;
import app.backend.paymentservice.infrastructure.kafka.message.service.ProcessedMessageService;
import app.backend.paymentservice.infrastructure.kafka.outbox.service.OutboxEventService;
import app.backend.paymentservice.payment.entity.PaymentStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentEventHandler {

	private final PaymentGateway paymentGateway;
	private final OutboxEventService outboxEventService;
	private final ProcessedMessageService processedMessageService;

	@Transactional
	public void handlePaymentCheckRequired(
		final PaymentCheckedEvent event,
		final KafkaEventMeta meta,
		final String consumerName
	) {

		validateEventId(meta);

		boolean marked = processedMessageService.markAsProcessed(
			meta.eventId(),
			consumerName,
			meta.eventType(),
			meta.topicName(),
			meta.partitionNo(),
			meta.offsetNo()
		);

		if (!marked) {
			log.info(
				"이미 처리된 결제 확인 이벤트입니다. eventId={}, consumerName={}",
				meta.eventId(),
				consumerName
			);
			return;
		}

		try {
			PaymentResponse paymentInfo = paymentGateway.getPayment(event.paymentKey());

			PaymentStatus status = paymentInfo.getStatus();

			if (status.isApproved()) {
				publishPaymentApproved(event);
				return;
			}

			if (status.isFailed()) {
				publishPaymentFailed(event);
				return;
			}

			publishPaymentUnknown(event);

		} catch (Exception e) {
			log.warn(
				"PG 결제 상태 조회 실패. orderId={}, orderNumber={}, paymentKey={}, message={}",
				event.orderId(),
				event.orderNumber(),
				event.paymentKey(),
				e.getMessage()
			);

			publishPaymentUnknown(event);
		}
	}

	private void publishPaymentApproved(final PaymentCheckedEvent event) {
		PaymentApprovedEvent approvedEvent = PaymentApprovedEvent.from(
			event.orderId(),
			event.orderNumber(),
			event.paymentKey()
		);

		outboxEventService.publish(
			"Order",
			String.valueOf(event.orderId()),
			"PaymentApproved",
			EventTopics.PAYMENT_APPROVED,
			approvedEvent
		);
	}

	private void publishPaymentFailed(final PaymentCheckedEvent event) {
		PaymentFailedEvent failedEvent = PaymentFailedEvent.from(
			event.orderId(),
			event.orderNumber(),
			event.paymentKey()
		);

		outboxEventService.publish(
			"Order",
			String.valueOf(event.orderId()),
			"PaymentFailed",
			EventTopics.PAYMENT_FAILED,
			failedEvent
		);
	}

	private void publishPaymentUnknown(final PaymentCheckedEvent event) {
		PaymentUnknownEvent unknownEvent = PaymentUnknownEvent.from(
			event.orderId(),
			event.orderNumber(),
			event.paymentKey()
		);

		outboxEventService.publish(
			"Order",
			String.valueOf(event.orderId()),
			"PaymentUnknown",
			EventTopics.PAYMENT_UNKNOWN,
			unknownEvent
		);
	}

	private void validateEventId(final KafkaEventMeta meta) {
		if (meta.eventId() == null || meta.eventId().isBlank()) {
			throw new IllegalStateException("eventId header가 없습니다.");
		}
	}

}
