package app.backend.orderservice.infrastructure.kafka.outbox.exception;

import app.backend.orderservice.global.error.exception.DomainErrorCode;
import app.backend.orderservice.global.error.exception.DomainException;

public class OutboxEventException extends DomainException {
	public OutboxEventException(DomainErrorCode errorCode) {
		super(errorCode);
	}
}
