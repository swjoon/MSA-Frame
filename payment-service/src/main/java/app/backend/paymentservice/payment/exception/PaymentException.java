package app.backend.paymentservice.payment.exception;

import app.backend.paymentservice.global.error.exception.DomainErrorCode;
import app.backend.paymentservice.global.error.exception.DomainException;

public class PaymentException extends DomainException {
	public PaymentException(DomainErrorCode errorCode) {
		super(errorCode);
	}
}
