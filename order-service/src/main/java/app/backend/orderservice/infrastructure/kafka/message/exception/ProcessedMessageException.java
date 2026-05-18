package app.backend.orderservice.infrastructure.kafka.message.exception;

import app.backend.orderservice.global.error.exception.DomainErrorCode;
import app.backend.orderservice.global.error.exception.DomainException;

public class ProcessedMessageException extends DomainException {
	public ProcessedMessageException(DomainErrorCode errorCode) {
		super(errorCode);
	}
}
