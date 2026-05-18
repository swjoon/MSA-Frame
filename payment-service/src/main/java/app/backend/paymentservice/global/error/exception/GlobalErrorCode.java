package app.backend.paymentservice.global.error.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GlobalErrorCode implements DomainErrorCode {

	INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "GL001", "올바르지 않은 입력값"),
	METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "GL002", "올바르지 않은 HTTP 메서드"),
	ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "GL003", "값을 찾지 못함"),
	HANDLE_ACCESS_DENIED(HttpStatus.FORBIDDEN, "GL004", "요청이 거부됨"),
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "GL005", "서버 내부 오류 발생"),
	AUTHENTICATION_FAIL(HttpStatus.UNAUTHORIZED, "GL006", "Security 인증 오류 발생"),
	DUPLICATE_DATA(HttpStatus.CONFLICT, "GL007", "중복된 데이터 값"),
	WRONG_DATE(HttpStatus.BAD_REQUEST, "GL008", "올바르지 않은 날짜값");

	private final HttpStatus status;
	private final String code;
	private final String message;

}
