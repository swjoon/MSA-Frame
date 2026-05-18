package app.backend.paymentservice.infrastructure.kafka.event.dto.request;

public record PaymentApprovedEvent(
	Long orderId,
	String orderNumber,
	String paymentKey
) {
	public static PaymentApprovedEvent from(Long orderId, String orderNumber, String paymentKey) {
		return new PaymentApprovedEvent(orderId, orderNumber, paymentKey);
	}
}