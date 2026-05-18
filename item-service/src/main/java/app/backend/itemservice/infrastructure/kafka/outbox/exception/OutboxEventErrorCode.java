package app.backend.itemservice.infrastructure.kafka.outbox.exception;

import org.springframework.http.HttpStatus;

import app.backend.itemservice.global.error.exception.DomainErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OutboxEventErrorCode implements DomainErrorCode {

	OUTBOX_EVENT_NOT_FOUND(HttpStatus.NOT_FOUND, "OE001", "EVENT 내역을 찾을 수 없습니다."),
	JSON_PROCESSING_ERROR(HttpStatus.BAD_REQUEST, "OE002", "JSON 직렬화 과정에서 오류가 발생했습니다.");

	private final HttpStatus status;
	private final String code;
	private final String message;

}
