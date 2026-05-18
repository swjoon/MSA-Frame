package app.backend.orderservice.infrastructure.client.payment.dto.res;

import java.time.OffsetDateTime;

import app.backend.orderservice.infrastructure.client.payment.constants.PaymentStatus;

public record ConfirmPaymentResDto(
	Long paymentId,
	String paymentKey,
	String paymentMethod,
	PaymentStatus status,
	Long totalAmount,
	OffsetDateTime approvedAt,
	String receiptUrl
) {
}
