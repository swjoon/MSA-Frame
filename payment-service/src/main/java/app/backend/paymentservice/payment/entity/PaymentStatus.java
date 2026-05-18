package app.backend.paymentservice.payment.entity;

import lombok.Getter;

@Getter
public enum PaymentStatus {

	READY, DONE, CANCELED, ABORTED;

}
