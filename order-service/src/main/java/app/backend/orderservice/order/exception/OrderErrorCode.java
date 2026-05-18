package app.backend.orderservice.order.exception;

import org.springframework.http.HttpStatus;

import app.backend.orderservice.global.error.exception.DomainErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderErrorCode implements DomainErrorCode {
	ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "O001", "주문정보를 찾을 수 없습니다."),
	ITEM_NOT_FOUND(HttpStatus.BAD_GATEWAY, "O002", "상품 서비스에서 상품 정보를 조회하지 못했습니다."),
	ITEM_STOCK_SHORTAGE(HttpStatus.BAD_REQUEST, "O003", "상품 재고가 부족합니다."),
	ITEM_STOCK_DEDUCT_FAILED(HttpStatus.CONFLICT, "O004", "상품 재고 차감에 실패했습니다."),
	INVALID_ORDER_STATUS(HttpStatus.CONFLICT, "O005", "현재 주문 상태에서는 처리할 수 없습니다."),

	ITEM_STOCK_RECOVERY_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "O006", "상품 재고 복구에 실패했습니다."),

	PAYMENT_CONFIRM_FAILED(HttpStatus.BAD_GATEWAY, "O007", "결제 승인 처리에 실패했습니다."),
	PAYMENT_TIMEOUT(HttpStatus.ACCEPTED, "O008", "결제 처리 결과를 확인 중입니다."),
	ORDER_PAYMENT_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "O009", "주문 결제 처리 중 오류가 발생했습니다.");

	private final HttpStatus status;
	private final String code;
	private final String message;
}
