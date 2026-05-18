package app.backend.orderservice.order.exception;

import app.backend.orderservice.global.error.exception.DomainErrorCode;
import app.backend.orderservice.global.error.exception.DomainException;

public class OrderException extends DomainException {
	public OrderException(final DomainErrorCode errorCode) {
		super(errorCode);
	}
}
