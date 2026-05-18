package app.backend.paymentservice.infrastructure.kafka.outbox.exception;

import app.backend.paymentservice.global.error.exception.DomainErrorCode;
import app.backend.paymentservice.global.error.exception.DomainException;

public class OutboxEventException extends DomainException {
	public OutboxEventException(DomainErrorCode errorCode) {
		super(errorCode);
	}
}
