package app.backend.paymentservice.payment.exception;

import org.springframework.http.HttpStatus;

import app.backend.paymentservice.global.error.exception.DomainErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentErrorCode implements DomainErrorCode {
	PAYMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "P001", "Payment not found"),
	PAYMENT_API_ERROR(HttpStatus.BAD_REQUEST, "P002", "결제 오류가 발생했습니다."),
	PAYMENT_API_CANCEL_FAILED(HttpStatus.BAD_REQUEST, "P003", "결제 취소 오류가 발생했습니다.");

	private final HttpStatus status;
	private final String code;
	private final String message;

}
