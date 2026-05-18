package app.backend.itemservice.infrastructure.kafka.outbox.exception;

import app.backend.itemservice.global.error.exception.DomainErrorCode;
import app.backend.itemservice.global.error.exception.DomainException;

public class OutboxEventException extends DomainException {
	public OutboxEventException(DomainErrorCode errorCode) {
		super(errorCode);
	}
}
