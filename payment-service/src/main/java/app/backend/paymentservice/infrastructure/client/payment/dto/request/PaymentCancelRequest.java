package app.backend.paymentservice.infrastructure.client.payment.dto.request;

public record PaymentCancelRequest(
	String cancelReason,
	Long cancelAmount
) {

	public static PaymentCancelRequest from(final String cancelReason, final Long cancelAmount) {
		return new PaymentCancelRequest(cancelReason, cancelAmount);
	}
}
