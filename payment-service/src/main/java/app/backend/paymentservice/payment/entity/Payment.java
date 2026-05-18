package app.backend.paymentservice.payment.entity;

import java.time.OffsetDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "tbl_payment")
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private Long orderId;

	@Column(unique = true)
	private String orderNumber;

	@Column(length = 100, unique = true)
	private String paymentKey;

	@Column(length = 20)
	private String paymentMethod;

	@Column(length = 50)
	private String cardNumber;

	@Column(length = 20)
	private String cardApproveNo;

	@Column(length = 1024)
	private String receiptUrl;

	@Column(nullable = false)
	private Long totalAmount;

	@Column
	private OffsetDateTime approvedAt;

	@Column
	private String cancelReason;

	@Column
	private PaymentStatus status;

	@Column
	private OffsetDateTime canceledAt;

	public static Payment create(Long orderId, String orderNumber, String paymentKey, Long totalAmount) {

		return Payment.builder()
			.orderId(orderId)
			.orderNumber(orderNumber)
			.paymentKey(paymentKey)
			.totalAmount(totalAmount)
			.build();
	}

	public void update(String paymentMethod, PaymentStatus status, OffsetDateTime approvedAt) {
		this.paymentMethod = paymentMethod;
		this.status = status;
		this.approvedAt = approvedAt;
	}

	public void updateCardInfo(String cardNumber, String cardApproveNo) {
		this.cardNumber = cardNumber;
		this.cardApproveNo = cardApproveNo;
	}

	public void updateReceiptUrl(String receiptUrl) {
		this.receiptUrl = receiptUrl;
	}

}
