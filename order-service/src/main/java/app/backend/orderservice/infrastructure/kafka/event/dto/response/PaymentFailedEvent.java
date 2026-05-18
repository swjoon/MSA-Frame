package app.backend.orderservice.infrastructure.kafka.event.dto.response;

public record PaymentFailedEvent(
	Long orderId,
	String orderNumber,
	String paymentKey
) {
}