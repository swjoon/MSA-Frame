package app.backend.orderservice.global.response;

import org.slf4j.MDC;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import app.backend.orderservice.global.constant.TraceConstants;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@JsonInclude(Include.NON_NULL)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {

	@NotNull
	private final boolean isSuccess;

	@NotNull
	private final String code;

	@NotNull
	private final String message;

	@NotNull
	private final String traceId;

	private final T data;

	/**
	 * 데이터를 포함하지 않는 응답 객체
	 *
	 * @param isSuccess 응답 성공여부
	 * @param code (예: "200", "GL001" 등)
	 * @param message 응답 메시지
	 * @param <T> 응답 데이터 타입 (null 로 반환됨)
	 * @return ApiResponse
	 */
	public static <T> ApiResponse<T> of(@NotNull final Boolean isSuccess,
		@NotNull final String code,
		@NotNull final String message) {
		return new ApiResponse<T>(isSuccess, code, message, getTraceId(), null);
	}

	/**
	 * 데이터를 포함하지 않는 응답 객체
	 *
	 * @param isSuccess 응답 성공여부
	 * @param status HTTP 상태 코드 (예: HttpStatus.OK)
	 * @param message 응답 메시지
	 * @param <T> 응답 데이터 타입 (null 로 반환됨)
	 * @return ApiResponse
	 */
	public static <T> ApiResponse<T> of(@NotNull final Boolean isSuccess,
		@NotNull final HttpStatus status,
		@NotNull final String message) {
		return new ApiResponse<T>(isSuccess, String.valueOf(status.value()), message, getTraceId(), null);
	}

	/**
	 * 데이터를 포함하는 응답 객체
	 *
	 * @param isSuccess 응답 성공여부
	 * @param code (예: "200", "GL001" 등)
	 * @param message 응답 메시지
	 * @param <T> 응답 데이터 타입 (null 로 반환됨)
	 * @return ApiResponse
	 */
	public static <T> ApiResponse<T> of(@NotNull final Boolean isSuccess,
		@NotNull final String code,
		@NotNull final String message,
		@NotNull final T data) {
		return new ApiResponse<T>(isSuccess, code, message, getTraceId(), data);
	}

	/**
	 * 데이터를 포함하는 응답 객체
	 *
	 * @param isSuccess 응답 성공여부
	 * @param status HTTP 상태 코드 (예: HttpStatus.OK)
	 * @param message 응답 메시지
	 * @param <T> 응답 데이터 타입 (null 로 반환됨)
	 * @return ApiResponse
	 */
	public static <T> ApiResponse<T> of(@NotNull final Boolean isSuccess,
		@NotNull final HttpStatus status,
		@NotNull final String message,
		@NotNull final T data) {
		return new ApiResponse<T>(isSuccess, String.valueOf(status.value()), message, getTraceId(), data);
	}

	private static String getTraceId() {
		String traceId = MDC.get(TraceConstants.TRACE_ID_MDC_KEY);
		return traceId != null ? traceId : TraceConstants.NO_TRACE;
	}
}
