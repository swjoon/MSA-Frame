package app.backend.paymentservice.infrastructure.client.payment.dto.response;

import java.time.OffsetDateTime;

import app.backend.paymentservice.payment.entity.PaymentStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaymentResponse {
	private String paymentKey;
	private String orderId;
	private String method;
	private OffsetDateTime approvedAt;
	private Long totalAmount;
	private PaymentStatus status;
	private Card card;
	private Receipt receipt;

	@Getter
	public static class Card {
		private String number;
		private String approveNo;
	}

	@Getter
	public static class Receipt {
		private String url;
	}
}
