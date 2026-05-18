package app.backend.orderservice.infrastructure.client.payment.exception;

import app.backend.orderservice.infrastructure.client.error.InternalServiceException;

public class PaymentInternalException extends InternalServiceException {

	public PaymentInternalException(final String code, final String message, final int status, final String traceId) {
		super("payment-service", code, message, status, traceId);
	}
}
