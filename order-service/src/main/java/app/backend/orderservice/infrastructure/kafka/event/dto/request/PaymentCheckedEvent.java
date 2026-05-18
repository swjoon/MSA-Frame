package app.backend.orderservice.infrastructure.kafka.event.dto.request;

public record PaymentCheckedEvent(
	Long orderId,
	String orderNumber,
	String paymentKey
) {
	public static PaymentCheckedEvent from(Long orderId, String orderNumber, String paymentKey) {
		return new PaymentCheckedEvent(orderId, orderNumber, paymentKey);
	}
}
