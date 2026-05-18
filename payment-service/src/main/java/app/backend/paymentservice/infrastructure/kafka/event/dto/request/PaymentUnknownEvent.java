package app.backend.paymentservice.infrastructure.kafka.event.dto.request;

public record PaymentUnknownEvent(
	Long orderId,
	String orderNumber,
	String paymentKey
) {
	public static PaymentUnknownEvent from(
		Long orderId,
		String orderNumber,
		String paymentKey
	) {
		return new PaymentUnknownEvent(orderId, orderNumber, paymentKey);
	}
}