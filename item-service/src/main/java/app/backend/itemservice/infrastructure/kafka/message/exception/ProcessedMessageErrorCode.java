package app.backend.itemservice.infrastructure.kafka.message.exception;

import org.springframework.http.HttpStatus;

import app.backend.itemservice.global.error.exception.DomainErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProcessedMessageErrorCode implements DomainErrorCode {

	PROCESSED_MESSAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "PM001", "발행된 메시지를 찾을 수 없습니다."),
	PROCESSED_MESSAGE_ALREADY_CONSUMED(HttpStatus.CONFLICT, "PM002", "이미 처리된 이벤트 입니다.");

	private final HttpStatus status;
	private final String code;
	private final String message;
}
