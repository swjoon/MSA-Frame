package app.backend.itemservice.infrastructure.kafka.message.exception;

import app.backend.itemservice.global.error.exception.DomainErrorCode;
import app.backend.itemservice.global.error.exception.DomainException;

public class ProcessedMessageException extends DomainException {
	public ProcessedMessageException(DomainErrorCode errorCode) {
		super(errorCode);
	}
}
