package app.backend.orderservice.infrastructure.kafka.event.dto.response;

public record PaymentApprovedEvent(
	Long orderId,
	String orderNumber,
	String paymentKey
) {
}