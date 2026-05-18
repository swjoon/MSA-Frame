package app.backend.paymentservice.infrastructure.kafka.message.exception;

import app.backend.paymentservice.global.error.exception.DomainErrorCode;
import app.backend.paymentservice.global.error.exception.DomainException;

public class ProcessedMessageException extends DomainException {
	public ProcessedMessageException(DomainErrorCode errorCode) {
		super(errorCode);
	}
}
