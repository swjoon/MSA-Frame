package app.backend.orderservice.order.dto.res;

import java.time.LocalDateTime;

import app.backend.orderservice.infrastructure.client.payment.dto.res.ConfirmPaymentResDto;
import app.backend.orderservice.order.entity.Order;
import app.backend.orderservice.order.entity.OrderStatus;

public record GetOrderWithPaymentDto(
	Long orderId,
	Long memberId,
	Long itemId,
	String orderNumber,
	int stock,
	Long totalPrice,
	LocalDateTime orderDateTime,
	OrderStatus status,
	ConfirmPaymentResDto paymentInfo
) {
	public static GetOrderWithPaymentDto from(final Order order, final ConfirmPaymentResDto paymentDto) {

		return new GetOrderWithPaymentDto(
			order.getId(),
			order.getMemberId(),
			order.getItemId(),
			order.getOrderNumber(),
			order.getStock(),
			order.getTotalPrice(),
			order.getOrderDateTime(),
			order.getStatus(),
			paymentDto
		);
	}
}
