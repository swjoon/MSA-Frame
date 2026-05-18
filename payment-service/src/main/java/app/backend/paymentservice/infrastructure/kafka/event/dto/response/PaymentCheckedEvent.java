package app.backend.paymentservice.infrastructure.kafka.event.dto.response;

public record PaymentCheckedEvent(
	Long orderId,
	String orderNumber,
	String paymentKey
) {
	public static PaymentCheckedEvent from(Long orderId, String orderNumber, String paymentKey) {
		return new PaymentCheckedEvent(orderId, orderNumber, paymentKey);
	}
}
