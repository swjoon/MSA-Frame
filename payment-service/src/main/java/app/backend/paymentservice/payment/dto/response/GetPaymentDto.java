package app.backend.paymentservice.payment.dto.response;

import java.time.OffsetDateTime;

import app.backend.paymentservice.payment.entity.PaymentStatus;
import app.backend.paymentservice.payment.entity.Payment;

public record GetPaymentDto(
	Long paymentId,
	String paymentKey,
	String paymentMethod,
	PaymentStatus status,
	Long totalAmount,
	OffsetDateTime approvedAt,
	String receiptUrl
) {
	public static GetPaymentDto from(Payment payment) {
		return new GetPaymentDto(
			payment.getId(),
			payment.getPaymentKey(),
			payment.getPaymentMethod(),
			payment.getStatus(),
			payment.getTotalAmount(),
			payment.getApprovedAt(),
			payment.getReceiptUrl()
		);
	}
}
