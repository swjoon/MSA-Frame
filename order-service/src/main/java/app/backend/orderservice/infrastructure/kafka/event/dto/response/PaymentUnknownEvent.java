package app.backend.orderservice.infrastructure.kafka.event.dto.response;

public record PaymentUnknownEvent(
	Long orderId,
	String orderNumber,
	String paymentKey
) {
}
