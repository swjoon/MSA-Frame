package app.backend.paymentservice.infrastructure.client.payment.dto.response;

import java.time.OffsetDateTime;

public record PaymentCancelResponse(
	String status,
	String cancelReason,
	Long canceledAmount,
	OffsetDateTime canceledAt
) {
}
