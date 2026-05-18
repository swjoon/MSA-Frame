package app.backend.paymentservice.infrastructure.kafka.event.dto.request;

public record PaymentFailedEvent(
	Long orderId,
	String orderNumber,
	String paymentKey
) {
	public static PaymentFailedEvent from(
		Long orderId,
		String orderNumber,
		String paymentKey
	) {
		return new PaymentFailedEvent(orderId, orderNumber, paymentKey);
	}
}