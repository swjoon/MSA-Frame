package app.backend.paymentservice.payment.entity;

import lombok.Getter;

@Getter
public enum PaymentStatus {

	READY, DONE, CANCELED, ABORTED;

	public boolean isApproved() {
		return this == DONE;
	}

	public boolean isFailed() {
		return this == ABORTED
			|| this == CANCELED;
	}

	public boolean isUnknown() {
		return !isApproved() && !isFailed();
	}
}
