package app.backend.orderservice.global.error;

import java.net.BindException;
import java.nio.file.AccessDeniedException;

import org.slf4j.MDC;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.server.MethodNotAllowedException;

import app.backend.orderservice.global.constant.TraceConstants;
import app.backend.orderservice.global.error.exception.DomainErrorCode;
import app.backend.orderservice.global.error.exception.DomainException;
import app.backend.orderservice.global.error.exception.GlobalErrorCode;
import app.backend.orderservice.global.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * 지원하지 않는 HTTP 메소드 호출
	 *
	 * @param ex
	 * @return ResponseEntity
	 */
	@ExceptionHandler(MethodNotAllowedException.class)
	public ResponseEntity<ApiResponse<Void>> handleMethodNotAllowedException(
		MethodNotAllowedException ex) {

		log.error("handleMethodNotAllowedException", ex);
		final DomainErrorCode errorCode = GlobalErrorCode.METHOD_NOT_ALLOWED;

		return buildErrorResponse(errorCode);
	}

	/**
	 * 지원하지 않는 HTTP 메소드 호출
	 *
	 * @param ex
	 * @return ResponseEntity
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ApiResponse<Void>> handleHttpRequestMethodNotSupportedException(
		HttpRequestMethodNotSupportedException ex) {

		log.error("handleHttpRequestMethodNotSupportedException", ex);
		final DomainErrorCode errorCode = GlobalErrorCode.METHOD_NOT_ALLOWED;

		return buildErrorResponse(errorCode);
	}

	/**
	 * HandlerMethodValidationException 발생 시(단일 값, @Valid 또는 @Validated 에서 바인딩 에러)
	 *
	 * @param ex
	 * @return ResponseEntity
	 */
	@ExceptionHandler(HandlerMethodValidationException.class)
	public ResponseEntity<ApiResponse<Void>> handleMethodValidationException(
		HandlerMethodValidationException ex
	) {

		log.error("handleMethodValidationException", ex);
		final DomainErrorCode errorCode = GlobalErrorCode.INVALID_INPUT_VALUE;

		return buildErrorResponse(errorCode);
	}

	/**
	 * BindException 발생 시(객체, @Valid 또는 @Validated 에서 바인딩 에러)
	 *
	 * @param ex
	 * @return ResponseEntity
	 */
	@ExceptionHandler(BindException.class)
	public ResponseEntity<ApiResponse<Void>> handleBindException(BindException ex) {

		log.error("handleBindException", ex);
		final DomainErrorCode errorCode = GlobalErrorCode.INVALID_INPUT_VALUE;

		return buildErrorResponse(errorCode);
	}

	/**
	 * 권한이 없는 요청 시
	 *
	 * @param ex
	 * @return ResponseEntity
	 */
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ApiResponse<Void>> handleAccessDeniedException(
		AccessDeniedException ex
	) {

		log.error("handleAccessDeniedException", ex);
		final DomainErrorCode errorCode = GlobalErrorCode.HANDLE_ACCESS_DENIED;

		return buildErrorResponse(errorCode);
	}

	/**
	 * 중복된 데이터 저장
	 *
	 * @param ex
	 * @return ResponseEntity
	 */
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ApiResponse<Void>> handleDataIntegrityViolationException(
		DataIntegrityViolationException ex
	) {

		log.error("handleDataIntegrityViolationException", ex);
		final DomainErrorCode errorCode = GlobalErrorCode.DUPLICATE_DATA;

		return buildErrorResponse(errorCode);
	}

	/**
	 * Domain 에러 발 시
	 *
	 * @param ex
	 * @return ResponseEntity
	 */
	@ExceptionHandler(DomainException.class)
	public ResponseEntity<ApiResponse<Void>> handleDomainException(DomainException ex) {

		log.error("handleDomainException", ex);

		return buildErrorResponse(ex.getDomainErrorCode());
	}

	/**
	 * 처리되지 않은 모든 예외에 대한 글로벌 핸들링
	 *
	 * @param ex
	 * @return ResponseEntity
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<Void>> handleException(Exception ex) {

		log.error("handleException", ex);
		DomainErrorCode errorCode = GlobalErrorCode.INTERNAL_SERVER_ERROR;

		return buildErrorResponse(errorCode);
	}

	private ResponseEntity<ApiResponse<Void>> buildErrorResponse(DomainErrorCode errorCode) {
		String traceId = MDC.get("traceId");

		return ResponseEntity.status(errorCode.getStatus())
			.header(TraceConstants.TRACE_ID_HEADER, traceId != null ? traceId : TraceConstants.NO_TRACE)
			.body(ApiResponse.of(false, errorCode.getCode(), errorCode.getMessage()));
	}
}
